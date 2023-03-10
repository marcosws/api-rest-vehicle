package com.github.marcosws.vehicle.api.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long>{

	public Optional<UserEntity> findByUsername(String username);
	
	public Boolean existsByUsername(String username);
	
}
