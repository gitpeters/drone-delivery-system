package com.peter.scheduler;

import com.peter.model.AuditLog;
import com.peter.model.Drone;
import com.peter.repository.AuditLogRepository;
import com.peter.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DroneTaskScheduler {
    private final DroneRepository droneRepository;
    private final AuditLogRepository auditLogRepository;

    @Scheduled(cron = "0 */2 * * * *") // Run task every 2 minutes
    public void scheduleTask() {
        log.info("Starting periodic battery check.");

        List<Drone> drones = droneRepository.findAll();

        if (!drones.isEmpty()) {
            drones.forEach(drone -> {
                AuditLog auditLog = new AuditLog();
                auditLog.setDroneSerialNumber(drone.getSerialNumber());
                auditLog.setBatteryLevel(drone.getBatteryCapacity());
                auditLog.setTimestamp(LocalDateTime.now());
                auditLogRepository.save(auditLog);
                if (drone.getBatteryCapacity() < 25) {
                    log.warn("Drone {} has low battery: {}", drone.getSerialNumber(), drone.getBatteryCapacity());
                }
            });
        } else {
            log.info("No drones found. Skipping battery check.");
        }
    }

}
