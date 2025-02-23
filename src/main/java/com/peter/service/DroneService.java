package com.peter.service;

import com.peter.helper.DroneMapper;
import com.peter.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DroneService {
    private final DroneRepository droneRepository;
    private final DroneMapper droneHelper;
}
