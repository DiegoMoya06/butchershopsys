package com.bss.butchershopsys.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bss.butchershopsys.model.role.Role;
import com.bss.butchershopsys.model.user.AppUser;
import com.bss.butchershopsys.repo.RoleRepo;
import com.bss.butchershopsys.repo.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor 
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private final UserRepo userRepo;
	private final RoleRepo roleRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepo.findByUsername(username);
		if(user == null) {
			log.error("User {} not found in database.", username);
			throw new UsernameNotFoundException("User not found in database");
		} else {
			log.info("User {} found in database.");
		}
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> { 
			authorities.add(new SimpleGrantedAuthority(role.getName())); 
		});
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

	@Override
	public AppUser saveUser(AppUser appUser) {
		
		Optional<AppUser> optUser = Optional.ofNullable(userRepo.findByUsername(appUser.getUsername()));
		log.info("Saving Username: {}", appUser.getUsername());
		
		if(!optUser.isPresent()) {
			appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
			return userRepo.save(appUser);
		}
		else throw new RuntimeException("Username already exists.");
	}

	@Override
	public Role saveRole(Role role) {
		Optional<Role> optRole = Optional.ofNullable(roleRepo.findByName(role.getName()));
		log.info("Saving Role: {}", role.getName());
		
		if(optRole.isPresent()) return roleRepo.save(role);
		else throw new RuntimeException("Role name already exists.");
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		log.info("Adding Role {} to User {}", roleName, username);
		AppUser appUser = userRepo.findByUsername(username);
		Role role = roleRepo.findByName(roleName);
		appUser.getRoles().add(role);
	}

	@Override
	public AppUser getUser(String username) {
		Optional<AppUser> optUser = Optional.ofNullable(userRepo.findByUsername(username));
		log.info("Getting user: {}", username);
		
		if(optUser.isPresent()) return userRepo.findByUsername(username);
		else throw new UsernameNotFoundException(username);
	}

	@Override
	public List<AppUser> getUsers() {
		log.info("Getting All users");
		return userRepo.findAll();
	}

	

}
