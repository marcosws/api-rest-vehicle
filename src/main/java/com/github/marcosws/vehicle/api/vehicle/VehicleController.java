package com.github.marcosws.vehicle.api.vehicle;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {
	
	@GetMapping
	public String get(){
		return " - METHOD GET";
	}
	
	@GetMapping("/{id}")
	public String getById(@PathVariable("id") Long id){
		return " - METHOD GET" + id;
	}
	
	@PostMapping("/{id}")
	public String post(@PathVariable("id") Long id){
		return " - METHOD POST" + id;
	}
	
	@PutMapping("/{id}")
	public String put(@PathVariable("id") Long id){
		return " - METHOD PUT" + id;
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id){
		return " - METHOD DELETE" + id;
	}

}
