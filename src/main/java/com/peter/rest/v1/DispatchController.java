package com.peter.rest.v1;

import com.peter.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/drones")
@RequiredArgsConstructor
public class DispatchController {
    private final DroneService droneService;
}
