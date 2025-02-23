package com.peter.helper;

import com.peter.dto.MedicationDTO;
import com.peter.model.Medication;
import org.springframework.stereotype.Component;

@Component
public class MedicationMapper {

    public MedicationDTO toDTO(Medication medication) {
        if (medication == null) {
            return null;
        }
        return MedicationDTO.builder()
                .id(medication.getPublicId())
                .name(medication.getName())
                .code(medication.getCode())
                .weight(medication.getWeight())
                .image(medication.getImage())
                .build();
    }

    public Medication toEntity(MedicationDTO dto) {
        if (dto == null) {
            return null;
        }
        Medication medication = new Medication();
        medication.setName(dto.getName());
        medication.setCode(dto.getCode());
        medication.setWeight(dto.getWeight());
        medication.setImage(dto.getImage());
        return medication;
    }
}


