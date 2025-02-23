package com.peter.scheduler;

import com.peter.model.AuditLog;
import com.peter.repository.DroneRepository;
import com.peter.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskScheduler {
    private final DroneRepository droneRepository;

    // run task every 5 minutes
    @Scheduled(cron = "0 */5 * * * *")
    public void scheduleTask(){
        log.info("Starting periodic battery check.");
        droneRepository.findAll().forEach(drone->{
            AuditLog auditLog = new AuditLog();
            auditLog.setDroneSerialNumber(String.valueOf(drone.getSerialNumber()));
            auditLog.setBatteryLevel(drone.getBatteryCapacity());
            auditLog.setTimestamp(LocalDateTime.now());

            if(drone.getBatteryCapacity() < 25){
                log.warn("Drone {} has low battery: {}",
                        drone.getSerialNumber(),
                        drone.getBatteryCapacity()
                        );
            }
        });
    }
}
