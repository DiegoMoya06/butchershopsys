package com.bss.butchershopsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ButchershopsysApplication {

	public static void main(String[] args) {
		SpringApplication.run(ButchershopsysApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	CommandLineRunner run(UserService userService) {
//		return args -> {
//			userService.saveRole(new Role(1, "ROLE_USER", "Common User"));
//			userService.saveRole(new Role(1, "ROLE_MANAGER", "Manager"));
//			userService.saveRole(new Role(1, "ROLE_ADMIN", "Admin"));
//			userService.saveRole(new Role(1, "ROLE_SUPER_ADMIN", "Super Admin"));
//			
//			userService.saveUser(new AppUser(1, "JuanP", "1234", new ArrayList<>()));
//			userService.saveUser(new AppUser(1, "JuanP", "1234", new ArrayList<>()));
//			userService.saveUser(new AppUser(1, "PedroL", "5678", new ArrayList<>()));
//			
//			AppUser ap = new AppUser(1, "JuanP", "1234", new ArrayList<>());
//			
//			
//			userService.addRoleToUser("JuapP", "ROLE_ADMIN");
//			userService.addRoleToUser("PedroL", "ROLE_USER");
//			
//			
//		};
//	}
}
