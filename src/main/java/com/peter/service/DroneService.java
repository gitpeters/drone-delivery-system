package com.peter.service;

import com.peter.dto.DroneDTO;
import com.peter.dto.MedicationDTO;
import com.peter.dto.MedicationLoadRequest;
import com.peter.enums.DroneModel;
import com.peter.enums.DroneState;
import com.peter.exceptions.DroneException;
import com.peter.helper.DroneMapper;
import com.peter.helper.MedicationMapper;
import com.peter.model.Drone;
import com.peter.model.Medication;
import com.peter.repository.DroneRepository;
import com.peter.repository.MedicationRepository;
import com.peter.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DroneService {
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final DroneMapper droneMapper;
    private final MedicationMapper medicationMapper;

    @Transactional
    public DroneDTO registerDrone(DroneDTO droneDTO) {
        log.info("Registering new drone with serial number: {} ", droneDTO.getSerialNumber());
        if(droneRepository.findBySerialNumber(droneDTO.getSerialNumber()).isPresent()) {
            throw new DroneException("Drone with serial number "+droneDTO.getSerialNumber()+" already exists.", 409);
        }
        Drone drone = droneMapper.toEntity(droneDTO);
        try{
            droneRepository.save(drone);
            return droneMapper.toDTO(drone);
        }catch(Exception e){
            log.error("Failed to save drone: {}", droneDTO.getSerialNumber(), e);
            throw new DroneException("Failed to save drone: " + droneDTO.getSerialNumber(), 500);
        }
    }

    @Transactional
    public DroneDTO loadDrone(String serialNumber, MedicationLoadRequest request) {
        if(request == null || request.getMedicationCodes().isEmpty()){
            throw new DroneException("At least one medication code is required to load drone.", 400);
        }

        Drone drone = droneRepository.findBySerialNumber(serialNumber).orElseThrow(()-> new DroneException("Drone not found", 404));

        if(!DroneState.IDLE.equals(drone.getState())){
            throw new DroneException("Drone is not available for loading", 400);
        }

        if(drone.getBatteryCapacity() < 25){
            throw new DroneException("Cannot load drone: Battery below 25%", 400);
        }

        List<Medication> medications = medicationRepository.findAllByCodeIn(request.getMedicationCodes());

        if(medications.isEmpty()){
            throw new DroneException("No medications found", 404);
        }

        double totalMedicationWeight = medications
                .stream()
                .mapToDouble(Medication::getWeight)
                .sum();

        if(totalMedicationWeight > drone.getWeightLimit()){
            throw new DroneException("Cannot load drone: Weight limit exceeded", 400);
        }

        drone.setState(DroneState.LOADING);
        medications.forEach(medication -> medication.setDrone(drone));
        medicationRepository.saveAll(medications);

        drone.setState(DroneState.LOADED);
        droneRepository.save(drone);
        return droneMapper.toDTO(drone);
    }

    public List<MedicationDTO> getLoadedMedications(String serialNumber) {
        List<Medication> loadedMedications = medicationRepository.findAllByDroneSerialNumber(serialNumber);

        if(loadedMedications.isEmpty()){
            throw new DroneException("No loaded medications found for this serial number: "+serialNumber, 404);
        }
        return loadedMedications.stream().map(medicationMapper::toDTO).toList();
    }

    public List<DroneDTO> getAvailableDrones() {
        List<Drone> droneList = droneRepository.findAllByState(DroneState.IDLE);
        if(droneList.isEmpty()){
            throw new DroneException("No available drones at the moment", 404);
        }
        return droneList.stream().map(droneMapper::toDTO).toList();
    }

    public Integer getDroneBatteryCapacity(String serialNumber) {
        return droneRepository.findBySerialNumber(serialNumber)
                .map(Drone::getBatteryCapacity)
                .orElseThrow(()-> new DroneException("Drone not found", 404));
    }
}
