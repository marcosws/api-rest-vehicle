package com.github.marcosws.vehicle.api.vehicle;

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
@RequestMapping("/api/v1/vehicle")
public class VehicleController {
	
	@Autowired
	private VehicleService service;
	
	@GetMapping
	public ResponseEntity<List<VehicleDTO>> get(){
		return new ResponseEntity<List<VehicleDTO>>(service.getVehicles(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<VehicleDTO> getById(@PathVariable("id") Long id){
		VehicleDTO vehicle = service.getVehicleById(id);
		return vehicle != null ? ResponseEntity.ok(vehicle) : ResponseEntity.notFound().build();
	}
	
	@PostMapping("/{automaker}")
	public ResponseEntity<VehicleDTO> post(@RequestBody VehicleEntity vehicle, @PathVariable("automaker") String automaker){

		VehicleDTO vehicleDTO = service.insert(vehicle, automaker);
		URI location = getUri(vehicleDTO.getId());
		return ResponseEntity.created(location).build();
		
	}
	
	@PutMapping("/{id}/{automaker}")
	public ResponseEntity<VehicleDTO> put(@RequestBody VehicleEntity vehicle, @PathVariable("id") Long id, @PathVariable("automaker") String automaker){
		
		vehicle.setId(id);
		VehicleDTO vehicleDTO = service.update(vehicle, id, automaker);
		return vehicleDTO != null ? ResponseEntity.ok(vehicleDTO) : ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<VehicleDTO> delete(@PathVariable("id") Long id){
		
        service.delete(id);
        return ResponseEntity.ok().build();
        
	}

}
