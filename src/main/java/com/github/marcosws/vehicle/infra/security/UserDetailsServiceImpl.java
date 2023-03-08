package com.github.marcosws.vehicle.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.marcosws.vehicle.api.user.UserEntity;
import com.github.marcosws.vehicle.api.user.UserRepository;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl  implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity user = repository.findByLogin(username);
		if(user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return user; 
	}

}
