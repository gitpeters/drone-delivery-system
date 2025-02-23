package com.peter.config;

import com.peter.enums.DroneModel;
import com.peter.enums.DroneState;
import com.peter.exceptions.MedicationException;
import com.peter.model.Drone;
import com.peter.model.Medication;
import com.peter.repository.DroneRepository;
import com.peter.repository.MedicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@Slf4j
public class DataLoader {
    @Bean
    public CommandLineRunner loadData(
            MedicationRepository medicationRepository,
            DroneRepository droneRepository
    ) {
        return (args) -> {

            // create 10 sample drones if there is not existing records
            if(droneRepository.count() == 0) {
                log.info("Loading sample drones ...");
                List<Drone> droneList = new ArrayList<>();
                for (int i = 0; i <= 10; i++) {
                    Drone drone = new Drone();
                    drone.setSerialNumber("DRONE_"+(i+1));
                    drone.setModel(DroneModel.values()[i % DroneModel.values().length]);
                    drone.setWeightLimit(this.generateWeight());
                    drone.setBatteryCapacity(ThreadLocalRandom.current().nextInt(25, 100));
                    drone.setState(DroneState.values()[i % DroneState.values().length]);
                    droneList.add(drone);
                }
                droneRepository.saveAll(droneList);
            }else{
                log.info("Drones data already exists, skipping data loading.");
            }

            if(medicationRepository.count() == 0) {
                log.info("Loading sample medications ...");
                List<Medication> medicationList = new ArrayList<>();
                String[] medicationNames = {"Aspirin", "Ibuprofen", "Paracetamol"};
                for (int i = 0; i <= 10; i++) {
                    Medication medication = new Medication();
                    medication.setName(medicationNames[i % medicationNames.length]);
                    medication.setWeight(this.generateWeight());
                    medication.setCode("MED_" + (i + 1));
                    medication.setImage("/images/medications/MED_" + (i + 1) + ".jpg");
                    medicationList.add(medication);
                }

                medicationRepository.saveAll(medicationList);
            }else {
                log.info("Medications data already exists, skipping data loading.");
            }

        };
    }

    private double generateWeight() {
        double value = ThreadLocalRandom.current().nextDouble(200.0, 500.0);
        return Math.round(value * 100.0) / 100.0;
    }
}
