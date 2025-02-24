package com.peter.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationLoadRequest {

    @NotEmpty(message = "Medication codes list cannot be empty.")
    private List<String> medicationCodes;
}
