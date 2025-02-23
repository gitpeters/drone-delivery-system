package com.peter.rest.v1;

import com.peter.dto.MedicationDTO;
import com.peter.service.MedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medications")
@RequiredArgsConstructor
public class MedicationController {
    private final MedicationService medicationService;

    @GetMapping
    public ResponseEntity<List<MedicationDTO>> getMedications() {
        List<MedicationDTO> medicationDTOList = medicationService.getAllMedications();
        return new ResponseEntity<>(medicationDTOList, HttpStatus.OK);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<MedicationDTO> createMedication(
            @RequestPart("medication") @Valid MedicationDTO medicationDTO,
            @RequestPart(value = "image") MultipartFile image) {
        MedicationDTO newMedicationDTO = medicationService.createMedication(medicationDTO, image);
        return new ResponseEntity<>(newMedicationDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{code}")
    public ResponseEntity<MedicationDTO> getMedicationByCode(@PathVariable String code) {
        MedicationDTO medicationDTO = medicationService.getMedication(code);
        return new ResponseEntity<>(medicationDTO, HttpStatus.OK);
    }

    @PatchMapping("/{code}")
    public ResponseEntity<MedicationDTO> updateMedication(
            @PathVariable String code,
            @RequestPart("medication") MedicationDTO medicationDTO,
            @RequestPart(value = "image", required = false) MultipartFile image)
    {
        MedicationDTO updatedMedication = medicationService.updateMedication(code, medicationDTO, image);
        return new ResponseEntity<>(updatedMedication, HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteMedication(@PathVariable String code) {
        medicationService.deleteMedication(code);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
