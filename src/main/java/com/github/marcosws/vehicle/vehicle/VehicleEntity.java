package com.github.marcosws.vehicle.vehicle;


import com.github.marcosws.vehicle.automaker.AutomakerEntity;
import com.github.marcosws.vehicle.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle_tab")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehicleEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", length = 100)
	private String name;
	
	@Column(name = "model", length = 100)
	private String model;
	
	@Column(name = "color", length = 100)
	private String color;
	
	@Column(name = "engine", length = 100)
	private String engine;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "automaker_id", nullable = false)
	private AutomakerEntity automaker;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
	
}
