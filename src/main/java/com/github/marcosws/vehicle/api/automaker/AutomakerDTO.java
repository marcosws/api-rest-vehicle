package com.github.marcosws.vehicle.api.automaker;

import org.modelmapper.ModelMapper;

import lombok.Data;

@Data
public class AutomakerDTO {
	
	private Long id;
	private String name;
	private String countryOrigin;
	
	
	public static AutomakerDTO create(AutomakerEntity automaker) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(automaker, AutomakerDTO.class);
	}

}
