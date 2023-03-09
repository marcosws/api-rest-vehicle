package com.github.marcosws.vehicle.api.automaker;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.marcosws.vehicle.support.Utils.getUri;

@RestController
@RequestMapping("/api/v1/automaker")
public class AutomakerController {
	
	@Autowired
	private AutomakerService service;
	
	@GetMapping
	public ResponseEntity<List<AutomakerDTO>> get(){
		return new ResponseEntity<List<AutomakerDTO>>(service.getAutomakers(),HttpStatus.OK);
	} 
	
	@GetMapping("/{id}")
	public ResponseEntity<AutomakerDTO> getById(@PathVariable("id") Long id){
		AutomakerDTO automaker = service.getAutomakerById(id);
		return automaker != null ? ResponseEntity.ok(automaker) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<AutomakerDTO> post(@RequestBody AutomakerEntity automaker){
		
		AutomakerDTO auto = service.insert(automaker);
		URI location = getUri(auto.getId());
		return ResponseEntity.created(location).build();
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AutomakerDTO> put(@PathVariable("id") Long id, @RequestBody AutomakerEntity automaker){
		
		automaker.setId(id);
		AutomakerDTO auto = service.update(automaker, id);
		return auto != null ? ResponseEntity.ok(auto) : ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<AutomakerDTO> delete(@PathVariable("id") Long id){
		
		service.delete(id);
		return ResponseEntity.ok().build();
		
	}
	

}
