package com.github.marcosws.vehicle.support.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.marcosws.vehicle.api.user.UserEntity;
import com.github.marcosws.vehicle.api.user.UserRepository;
import com.github.marcosws.vehicle.support.exception.ObjectNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
		return UserDetailsImpl.build(user);
		
	}
	
	
}
