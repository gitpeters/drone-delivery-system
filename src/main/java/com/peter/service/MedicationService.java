package com.peter.service;

import com.peter.helper.MedicationHelper;
import com.peter.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicationService {
    private final MedicationRepository medicationRepository;
    private final MedicationHelper medicationHelper;
}
