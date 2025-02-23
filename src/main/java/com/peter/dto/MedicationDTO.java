package com.peter.dto;

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
    private String name;
    private String code;
    private String image;
    private double weight;
}
