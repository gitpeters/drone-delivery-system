package com.peter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "audit_logs")
@Data
public class AuditLog extends BaseEntity {
    @Column(name = "drone_serial_number")
    private String droneSerialNumber;
    @Column(name = "battery_level")
    private Integer batteryLevel;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
