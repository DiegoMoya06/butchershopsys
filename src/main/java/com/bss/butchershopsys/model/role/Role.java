package com.bss.butchershopsys.model.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name="role")
public class Role {
	@Id
	@Column(name="id_role")
	@Setter(AccessLevel.NONE)
	private int id_role;
	@Column(name="name", nullable=false)
	private String name;
	@Column(name="description")
	private String description;
}
