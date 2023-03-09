package com.github.marcosws.vehicle.api.automaker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomakerRepository extends JpaRepository<AutomakerEntity,Long>{

	public AutomakerEntity findOneByName(String name);
}
