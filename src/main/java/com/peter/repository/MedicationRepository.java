package com.peter.repository;

import com.peter.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    Optional<Medication> findByCode(String code);

    List<Medication> findAllByOrderByCreatedAtDesc();

}
