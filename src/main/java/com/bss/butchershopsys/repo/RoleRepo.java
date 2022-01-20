package com.bss.butchershopsys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bss.butchershopsys.model.role.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	Role findByName(String name);
}
