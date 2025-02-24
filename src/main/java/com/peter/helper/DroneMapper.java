package com.peter.helper;

import com.peter.dto.DroneDTO;
import com.peter.model.Drone;
import org.springframework.stereotype.Component;

@Component
public class DroneMapper {

    public DroneDTO toDTO(Drone drone){
        if(drone == null){
            return null;
        }
        return DroneDTO.builder()
                .id(drone.getPublicId())
                .serialNumber(drone.getSerialNumber())
                .model(drone.getModel())
                .state(drone.getState())
                .weightLimit(drone.getWeightLimit())
                .batteryCapacity(drone.getBatteryCapacity())
                .build();
    }

    public Drone toEntity(DroneDTO droneDTO){
        if(droneDTO == null){
            return null;
        }
        Drone drone = new Drone();
        drone.setSerialNumber(droneDTO.getSerialNumber());
        drone.setModel(droneDTO.getModel());
        drone.setState(droneDTO.getState());
        drone.setWeightLimit(droneDTO.getWeightLimit());
        drone.setBatteryCapacity(droneDTO.getBatteryCapacity());
        return drone;
    }
}
