package com.bss.butchershopsys.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bss.butchershopsys.model.role.Role;
import com.bss.butchershopsys.model.user.AppUser;
import com.bss.butchershopsys.service.UserService;

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
