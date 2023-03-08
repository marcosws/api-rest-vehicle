package com.github.marcosws.vehicle.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

	@GetMapping
	public String get() {
		return "<h1>API de Cadastro de Veículos<h1>";
	}
	

	
	
	
}
