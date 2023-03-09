package com.github.marcosws.vehicle.api.automaker;

import java.util.List;

import com.github.marcosws.vehicle.api.user.UserEntity;
import com.github.marcosws.vehicle.api.vehicle.VehicleEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
	
	@Column(name = "name", length = 500, unique = true)
	private String name;
	
	@Column(name = "country_origin", length = 100)
	private String countryOrigin;
	
	@OneToMany(mappedBy = "automaker", fetch = FetchType.LAZY)
	private List<VehicleEntity> vehicles;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
	
}
