package com.peter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicationDTO {
    private Long id;
    private String name;
    private String code;
    private String image;
    private double weight;
}
