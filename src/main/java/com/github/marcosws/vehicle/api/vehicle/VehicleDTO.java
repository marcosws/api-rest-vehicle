package com.github.marcosws.vehicle.api.vehicle;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.marcosws.vehicle.api.automaker.AutomakerDTO;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class VehicleDTO {
	
	private Long id;
	private String name;
	private String model;
	private String color;
	private String engine;
	private AutomakerDTO automaker;
	
	public static VehicleDTO create(VehicleEntity vehicle) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(vehicle, VehicleDTO.class);
	}

}
