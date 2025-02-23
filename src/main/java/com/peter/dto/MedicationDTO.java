package com.peter.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicationDTO {
    private UUID id;
    @Pattern(regexp = "^[A-Za-z0-9-_]+$",
            message = "Medication name can only contain letters (A-Z, a-z), numbers (0-9), hyphens (-), and underscores (_).")
    private String name;

    @Pattern(regexp = "^[A-Z0-9_]+$",
            message = "Medication code must be in uppercase and can only contain letters (A-Z), numbers (0-9), and underscores (_).")
    private String code;
    private String image;
    private double weight;
}
