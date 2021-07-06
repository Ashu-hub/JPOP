package com.jpop.userService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpop.userService.model.User;
import com.jpop.userService.repos.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "User-Service")
@RestController
@RequestMapping("/users")
public class UserServiceController {
	
	@Autowired
	UserRepository userRepository;
	
	@ApiOperation(value = "Add a User", response = User.class)
	@PostMapping
	public ResponseEntity<User> addUser(@ApiParam(value = "User which will store in DB",
	          required = true)@RequestBody User user){
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(user));
	}
	
	@ApiOperation(value = "Get all Users", response = List.class)
	@GetMapping
	public ResponseEntity<List<User>> listAllUsers(){
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.getAllUsers());
	}
	
	@ApiOperation(value = "Get User By Id", response = User.class)
	@GetMapping(value = "/{userId}")
	public ResponseEntity<User> getUserById(@ApiParam(value = "User id by which user will be retrieved",
	          required = true)@PathVariable String userId) {
		
		User result = userRepository.getUserById(userId);
		if(null == result ) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@ApiOperation(value = "Delete a user by Id", response = String.class)
	@DeleteMapping("/{userId}")
	public String deleteUser(@ApiParam(value = "User id basis which user will be deleted",
	          required = true)@PathVariable String userId) {
		return userRepository.delete(userId);
	}
	
	@ApiOperation(value = "Update a user by Id", response = String.class)
	@PutMapping(value =  "/{userId}")
	public ResponseEntity<String> updateUser(@ApiParam(value = "User id basis which user will be updated",
	          required = true)@PathVariable String userId, @RequestBody User user){
		String userResult = userRepository.update(userId, user);
		if(null == userResult) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return  ResponseEntity.status(HttpStatus.OK).body(userResult);
	}
	
}
