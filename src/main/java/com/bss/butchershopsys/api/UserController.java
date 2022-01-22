package com.bss.butchershopsys.api;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bss.butchershopsys.model.role.Role;
import com.bss.butchershopsys.model.user.AppUser;
import com.bss.butchershopsys.service.UserService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	//GetMapping
	@RequestMapping(value="hello", method=RequestMethod.GET)
	public String hello() {
		return "Hello world";
	}
	
	// Find All
	@GetMapping("/users")
	public ResponseEntity<List<AppUser>> getUsers(){
		
		return ResponseEntity.ok().body(userService.getUsers());
	}
	
	// Create User
	@PostMapping("/user/save")
	public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
		return ResponseEntity.created(uri).body(userService.saveUser(user));
	}
	
	// Create Role
	@PostMapping("/role/save")
	public ResponseEntity<Role> saveRole(@RequestBody Role role) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
		return ResponseEntity.created(uri).body(userService.saveRole(role));
	}
	
	// Create Role
	@PostMapping("/role/addtouser")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
		userService.addRoleToUser(form.getUsername(), form.getRoleName());
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException {
		String authorizationHeader = request.getHeader(AUTHORIZATION);
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secretword".getBytes()); 
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				AppUser user = userService.getUser(username);
				
				String access_token = JWT.create()
						.withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 3 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algorithm);
				
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch(Exception exception) {
				response.setHeader("Error", exception.getMessage());
				response.setStatus(HttpStatus.FORBIDDEN.value());
				
				Map<String, String> error = new HashMap<>();
				error.put("error_message", exception.getMessage());
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else {
			throw new RuntimeException("Refresh token is missing");
		}
	}
	
//	//Find By Id
//	@RequestMapping(value="{id_user}")
//	public ResponseEntity<AppUser> getUserById(@PathVariable("iduser") Integer iduser){
//		Optional<AppUser> optionalUser = userDAO.findById(iduser);
//		if(optionalUser.isPresent()) {
//			return ResponseEntity.ok(optionalUser.get());
//		} else {
//			return ResponseEntity.noContent().build();
//		}
//	}
	
//	//Delete
//	@DeleteMapping(value="{id_user}")
//	public ResponseEntity<Void> deleteUser(@PathVariable("id_user") Integer id_user){
//		userDAO.deleteById(id_user);
//		return ResponseEntity.ok(null);
//	}
//	
//	//Update
//	@PutMapping
//	public ResponseEntity<AppUser> updateUser(@RequestBody AppUser user){
//		Optional<AppUser> optionalUser = userDAO.findById(user.getId_user());
//		
//		if(optionalUser.isPresent()) {
//			try {
//				AppUser updateUser = optionalUser.get();
//				updateUser.setUsername("UPDT_usr"+String.valueOf(Math.random()*2));
//				try {
//					Thread.sleep(7000);
//					System.out.println("SLEEEEEEEEP");
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				userDAO.save(updateUser);
//				System.out.println("UPDATEEEEEED");
//				return ResponseEntity.ok(updateUser);
//			} catch(ObjectOptimisticLockingFailureException e) {
//				System.out.println("Somebody has already updated the user");
//				return ResponseEntity.notFound().build();
//			}
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}
}

@Data
class RoleToUserForm {
	private String username;
	private String roleName;
}
