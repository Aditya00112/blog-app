package com.chirantan.project.controllers;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chirantan.project.payloads.ApiResponse;
import com.chirantan.project.payloads.UserDto;
import com.chirantan.project.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid@RequestBody UserDto userDto){
		UserDto createUserDto= this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid@RequestBody UserDto userDto, @PathVariable("userId") int uid){
		UserDto updatedUser=this.userService.updateUser(userDto,uid);
		return ResponseEntity.ok(updatedUser);
	}
	
	//give role wise access
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable int userId){
		this.userService.deleteUser(userId);
//		return new ResponseEntity(Map.of("message","user deleted successfully"),HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUsers()) ;
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable  int userId){
		UserDto userDto=this.userService.getUserById(userId);
		return new ResponseEntity<>(userDto,HttpStatus.ACCEPTED);
	}
	
	
}
