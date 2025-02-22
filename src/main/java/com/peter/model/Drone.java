package com.peter.model;

import com.peter.enums.DroneModel;
import com.peter.enums.DroneState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "drones")
@Data
public class Drone extends BaseEntity {
    @Column(name = "serial_number", unique = true)
    @Size(max = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "model")
    private DroneModel model;

    @Max(500)
    @Column(name = "weight_limit")
    private Double weightLimit;

    @Max(100)
    @Min(0)
    @Column(name = "battery_capacity")
    private Integer batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private DroneState state;


}
