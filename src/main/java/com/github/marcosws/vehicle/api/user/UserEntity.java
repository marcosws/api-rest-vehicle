package com.github.marcosws.vehicle.api.user;

//import java.util.Collection;
import java.util.List;

import com.github.marcosws.vehicle.api.automaker.AutomakerEntity;
import com.github.marcosws.vehicle.api.vehicle.VehicleEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
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
	
	@Column(name = "fullname", length = 500)
	private String fullname;
	
	@Column(name = "username", length = 500)
	private String username;
	
	@Column(name = "password", length = 1024)
	private String password;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<AutomakerEntity> automakers;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<VehicleEntity> vehicles;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles",
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<RoleEntity> roles;

	
}
