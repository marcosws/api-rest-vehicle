package com.github.marcosws.vehicle.user;

import java.util.List;

import com.github.marcosws.vehicle.automaker.AutomakerEntity;
import com.github.marcosws.vehicle.vehicle.VehicleEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_tab")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", length = 500)
	private String name;
	
	@Column(name = "login", length = 500)
	private String login;
	
	@Column(name = "password", length = 1024)
	private String password;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<AutomakerEntity> automakers;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<VehicleEntity> vehicles;
	
}
