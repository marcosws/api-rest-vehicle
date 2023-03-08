package com.github.marcosws.vehicle.api.automaker;

import java.util.List;

import com.github.marcosws.vehicle.api.user.UserEntity;
import com.github.marcosws.vehicle.api.vehicle.VehicleEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "automaker_tab")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AutomakerEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", length = 500)
	private String name;
	
	@Column(name = "country_origin", length = 100)
	private String countryOrigin;
	
	@OneToMany(mappedBy = "automaker", fetch = FetchType.LAZY)
	private List<VehicleEntity> vehicles;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
	
}
