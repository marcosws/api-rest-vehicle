package com.github.marcosws.vehicle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ApiRestVehicleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRestVehicleApplication.class, args);
	}

}
