package com.peter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DroneDeliverySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneDeliverySystemApplication.class, args);
	}

}
