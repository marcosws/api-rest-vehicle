package com.github.marcosws.vehicle.api.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long>{

	public UserEntity findByLogin(String login);
	
}
