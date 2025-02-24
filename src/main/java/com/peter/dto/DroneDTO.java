package com.peter.dto;

import com.peter.enums.DroneModel;
import com.peter.enums.DroneState;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DroneDTO {
    private UUID id;

    @Size(min = 1, max = 50,
            message = "Serial number must be between 1 and 50 characters long.")
    private String serialNumber;

    @NotNull(message = "Weight limit is required.")
    @Max(value = 500, message = "Weight limit cannot exceed 500 grams.")
    private Double weightLimit;

    @Min(value = 0,
            message = "Battery capacity cannot be less than 0%.")
    @Max(value = 100,
            message = "Battery capacity cannot exceed 100%.")
    private Integer batteryCapacity;

    @NotNull(message = "Drone state is required.")
    private String state;

    @NotNull(message = "Drone model is required.")
    private String model;
}
