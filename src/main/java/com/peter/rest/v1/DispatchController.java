package com.peter.rest.v1;

import com.peter.dto.DroneDTO;
import com.peter.dto.MedicationDTO;
import com.peter.dto.MedicationLoadRequest;
import com.peter.enums.DroneModel;
import com.peter.enums.DroneState;
import com.peter.service.DroneService;
import com.peter.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.peter.util.ValidationUtil.validateEnum;

@RestController
@RequestMapping("/api/v1/drones")
@RequiredArgsConstructor
public class DispatchController {
    private final DroneService droneService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid DroneDTO droneDTO){
        // Validate enums
        ResponseEntity<?> stateValidation = validateEnum(droneDTO.getState(), DroneState.class, "drone state");
        if (stateValidation != null) return stateValidation;

        ResponseEntity<?> modelValidation = validateEnum(droneDTO.getModel(), DroneModel.class, "drone model");
        if (modelValidation != null) return modelValidation;
        DroneDTO registeredDrone = droneService.registerDrone(droneDTO);
        return new ResponseEntity<>(registeredDrone, HttpStatus.CREATED);
    }

    @PostMapping("/{serialNumber}/load")
    public ResponseEntity<DroneDTO> load(@PathVariable String serialNumber,
                                         @RequestBody @Valid MedicationLoadRequest medicationCodes){
        DroneDTO loadedDrone = droneService.loadDrone(serialNumber, medicationCodes);
        return ResponseEntity.ok(loadedDrone);
    }

    @GetMapping("/{serialNumber}/medications")
    public ResponseEntity<List<MedicationDTO>> getMedications(@PathVariable String serialNumber){
        List<MedicationDTO> loadedMedications = droneService.getLoadedMedications(serialNumber);
        return ResponseEntity.ok(loadedMedications);
    }

    @GetMapping("/available")
    public ResponseEntity<List<DroneDTO>> getAvailableDrones(){
        List<DroneDTO> availableDrones = droneService.getAvailableDrones();
        return ResponseEntity.ok(availableDrones);
    }

    @GetMapping("/{serialNumber}/battery")
    public ResponseEntity<Integer> checkBattery(@PathVariable String serialNumber){
        return ResponseEntity.ok(droneService.getDroneBatteryCapacity(serialNumber));
    }
}
