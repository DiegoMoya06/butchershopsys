package com.bss.butchershopsys.service;

import java.util.List;

import com.bss.butchershopsys.model.role.Role;
import com.bss.butchershopsys.model.user.AppUser;


public interface UserService {
	AppUser saveUser(AppUser appUser);
	Role saveRole(Role role);
	void addRoleToUser(String username, String roleName);
	AppUser getUser(String username);
	List<AppUser> getUsers();
}
