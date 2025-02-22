package com.peter.dto;

import com.peter.enums.DroneModel;
import com.peter.enums.DroneState;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DroneDTO {
    private Long id;
    @Size(min = 1, max = 50)
    private String serialNumber;
    @Max(500)
    private Double weightLimit;
    @Max(100)
    @Min(0)
    private Integer batteryCapacity;
    private DroneState state;
    private DroneModel model;
}
