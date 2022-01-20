package com.bss.butchershopsys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bss.butchershopsys.model.user.AppUser;

public interface UserRepo extends JpaRepository<AppUser, Integer> {
	AppUser findByUsername(String username);
}
