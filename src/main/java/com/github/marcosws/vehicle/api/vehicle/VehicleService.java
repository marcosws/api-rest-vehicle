package com.github.marcosws.vehicle.api.vehicle;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.marcosws.vehicle.api.automaker.AutomakerEntity;
import com.github.marcosws.vehicle.api.automaker.AutomakerRepository;
import com.github.marcosws.vehicle.api.user.UserEntity;
import com.github.marcosws.vehicle.api.user.UserRepository;
import com.github.marcosws.vehicle.support.exception.ObjectNotFoundException;

@Service
public class VehicleService {
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AutomakerRepository automakerRepository;
	
	public List<VehicleDTO> getVehicles(){
		return vehicleRepository.findAll().stream().map(VehicleDTO::create).collect(Collectors.toList());
	}
	
	public VehicleDTO getVehicleById(Long id) {
		Optional<VehicleEntity> vehicle = vehicleRepository.findById(id);
		return vehicle.map(VehicleDTO::create).orElseThrow(() -> new ObjectNotFoundException("Veículo não encontrado"));
	}
	
	public VehicleDTO insert(VehicleEntity vehicle, String automakerName) {
		
		AutomakerEntity automaker = automakerRepository.findOneByName(automakerName);
		
		
		Assert.isNull(vehicle.getId(),"Não foi possivel atualizar o registro! VEHICLE");
		
		Optional<UserEntity> optionalUser = userRepository.findById(1L);
		UserEntity user = optionalUser.get();
		vehicle.setAutomaker(automaker);
		vehicle.setUser(user);
		return VehicleDTO.create(vehicleRepository.save(vehicle));
		
	}
	
	public VehicleDTO update(VehicleEntity vehicle, Long id, String automakerName) {
		
		Optional<VehicleEntity> optionalVehicle = vehicleRepository.findById(id);
		Optional<UserEntity> optionalUser = userRepository.findById(1L);
		AutomakerEntity automaker = automakerRepository.findOneByName(automakerName);
		UserEntity user = optionalUser.get();
		if(optionalVehicle.isPresent()) {
			VehicleEntity vehicleDB = optionalVehicle.get();
			vehicleDB.setName(vehicle.getName());
			vehicleDB.setModel(vehicle.getModel());
			vehicleDB.setColor(vehicle.getColor());
			vehicleDB.setEngine(vehicle.getEngine());
			vehicleDB.setAutomaker(automaker);
			vehicleDB.setUser(user);
			vehicleRepository.save(vehicleDB);
			return VehicleDTO.create(vehicleDB);
		}
		else {
			return null;
		}
		
	}
	
	public void delete(Long id) {
		vehicleRepository.deleteById(id);
	}
	

	
	
	
	
	
	
}
