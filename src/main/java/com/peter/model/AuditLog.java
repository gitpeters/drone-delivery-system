package com.peter.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


@Entity
@Table(name = "audit_logs")
@Data
public class AuditLog  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "drone_serial_number")
    private String droneSerialNumber;
    @Column(name = "battery_level")
    private Integer batteryLevel;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
