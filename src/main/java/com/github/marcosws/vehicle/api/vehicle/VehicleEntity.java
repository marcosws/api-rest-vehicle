package com.github.marcosws.vehicle.api.vehicle;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.marcosws.vehicle.api.automaker.AutomakerEntity;
import com.github.marcosws.vehicle.api.user.UserEntity;

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
