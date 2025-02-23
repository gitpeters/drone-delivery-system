package com.peter.service;

import com.peter.dto.MedicationDTO;
import com.peter.exceptions.MedicationException;
import com.peter.helper.MedicationMapper;
import com.peter.model.Medication;
import com.peter.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicationService {
    private static final String UPLOAD_DIRECTORY = "src/main/resources/images/medications/";
    private final MedicationRepository medicationRepository;
    private final MedicationMapper mapper;

    @Transactional
    public MedicationDTO createMedication(MedicationDTO medicationDTO, MultipartFile image) {
        log.info("Creating new medication: {}", medicationDTO.getName());

        if(medicationRepository.findByCode(medicationDTO.getCode()).isPresent()){
            throw new MedicationException("Medication with code "+medicationDTO.getCode()+" already exists", 409);
        }

        try{
            Medication medication = mapper.toEntity(medicationDTO);
            if(image!=null && !image.isEmpty()){
                String imagePath = this.saveImage(image);
                medication.setImage(imagePath);
            }
            medication = medicationRepository.save(medication);
            return mapper.toDTO(medication);
        }catch (Exception e){
            log.error("Error creating medication: {}", e.getMessage(), e);
            throw new MedicationException("Failed to create medication", 500);
        }
    }

    @Transactional(readOnly = true)
    public List<MedicationDTO> getAllMedications() {
        List<Medication> medications = medicationRepository.findAll();
        return medications.stream().map(mapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public MedicationDTO getMedication(String code) {
        Medication medication = this.getMedicationByCode(code);
        return mapper.toDTO(medication);
    }

    @Transactional
    public void deleteMedication(String code) {
        Medication medication = this.getMedicationByCode(code);

        if(medication.getDrone()!=null){
            throw new MedicationException("Cannot delete medication that is currently loaded on a drone", 423);
        }

        if(medication.getImage()!=null){
            this.deleteImage(medication.getImage());
        }
        medicationRepository.delete(medication);
        log.info("Medication with code {} has been deleted", code);
    }

    @Transactional
    public MedicationDTO updateMedication(String code, MedicationDTO medicationDTO, MultipartFile image) {
        Medication medication = this.getMedicationByCode(code);

        if(!code.equals(medicationDTO.getCode())
                && medicationRepository.findByCode(medicationDTO.getCode()).isPresent()){
            throw new MedicationException("Cannot update: Medication with code "+code+" already exists", 409);
        }
        medication.setCode(medicationDTO.getCode());
        medication.setName(medicationDTO.getName());
        medication.setWeight(medicationDTO.getWeight());

        if(image!=null && !image.isEmpty()){
            // delete already save medication image before updating it
            if(medication.getImage() != null){
                this.deleteImage(medication.getImage());
            }
            String imagePath = this.saveImage(image);
            medication.setImage(imagePath);
        }
        medication = medicationRepository.save(medication);
        return mapper.toDTO(medication);
    }

    @Transactional(readOnly = true)
    protected Medication getMedicationByCode(String code) {
        return medicationRepository.findByCode(code).orElseThrow(()->new MedicationException("Medication with code "+code+" not found", 404));
    }

    private String saveImage(MultipartFile image) {
        try{
            Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            String fileName = UUID.randomUUID().toString() + "_" +
                    StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath);
            return "/images/medications/" + fileName;
        } catch (IOException e) {
            log.error("Error saving image: {}", e.getMessage(), e);
            throw new MedicationException("Failed to save medication image", 500);
        }
    }

    private void deleteImage(String imagePath) {
        try {
            Path filePath = Paths.get(UPLOAD_DIRECTORY).resolve(
                    Paths.get(imagePath).getFileName());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("Error deleting image: {}", e.getMessage(), e);
            throw new MedicationException("Failed to delete medication image", 500);
        }
    }


}
