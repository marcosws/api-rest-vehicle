package com.github.marcosws.vehicle.api.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public List<UserEntity> getUsers(){
		return repository.findAll();
	}
	
}
