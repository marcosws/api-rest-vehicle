package com.github.marcosws.vehicle.api.automaker;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.marcosws.vehicle.api.user.UserEntity;
import com.github.marcosws.vehicle.api.user.UserRepository;
import com.github.marcosws.vehicle.infra.exception.ObjectNotFoundException;

@Service
public class AutomakerService {
	
	@Autowired
	private AutomakerRepository automakerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<AutomakerDTO> getAutomakers(){
		return automakerRepository.findAll().stream().map(AutomakerDTO::create).collect(Collectors.toList());
	}
	
	public AutomakerDTO getAutomakerById(Long id) {
		Optional<AutomakerEntity> automaker = automakerRepository.findById(id);
		return automaker.map(AutomakerDTO::create).orElseThrow(() -> new ObjectNotFoundException("A Montadora não foi encontrada!"));
	}
	
	public AutomakerDTO insert(AutomakerEntity automaker) {
		Assert.isNull(automaker.getId(),"Não foi possivel atualizar o registro!");
		Optional<UserEntity> optionalUser = userRepository.findById(1L);
		UserEntity user = optionalUser.get();
		automaker.setUser(user);
		return AutomakerDTO.create(automakerRepository.save(automaker));
	}
	
	public AutomakerDTO update(AutomakerEntity automaker, Long id) {
		
		Assert.notNull(id, "Não foi possivel atualizar o registro!");
		Optional<AutomakerEntity> optionalAutomaker = automakerRepository.findById(id);
		Optional<UserEntity> optionalUser = userRepository.findById(1L);
		UserEntity user = optionalUser.get();
		if(optionalAutomaker.isPresent()) {
			AutomakerEntity db = optionalAutomaker.get();
			db.setName(automaker.getName());
			db.setCountryOrigin(automaker.getCountryOrigin());
			db.setUser(user);
			automakerRepository.save(db);
			return AutomakerDTO.create(db);
		}
		else {
			return null;
		}
		
	}
	
	public void delete(Long id) {
		automakerRepository.deleteById(id);
	}
}
