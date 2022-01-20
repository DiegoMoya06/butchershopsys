package com.bss.butchershopsys.model.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.bss.butchershopsys.model.BaseEntity;
import com.bss.butchershopsys.model.role.Role;
import com.bss.butchershopsys.model.user.AppUser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class AppUser extends BaseEntity {
	
	@Id
	@Column(name="id_user")
	@Setter(AccessLevel.NONE)
	private int id_user;
	@Column(name="username", nullable=false)
	private String username;
	@Column(name="password", nullable=false)
	private String password;
	@Column(name="role")
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Role> roles = new ArrayList<>();
}
