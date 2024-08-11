package com.techlabs.app.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.RegisterDTO;
import com.techlabs.app.dto.UserRequestDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.service.UserService;
import com.techlabs.app.util.PageResponse;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	private static final Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Operation(summary = "Create A User")
	@PostMapping
	public ResponseEntity<UserResponseDto> createNewUser(@RequestBody RegisterDTO registerDto){
		logger.info("Adding A New User");
		UserResponseDto userResponseDto=userService.createNewUser(registerDto);
		return new ResponseEntity<UserResponseDto>(userResponseDto,HttpStatus.OK);
		
	}
	
	@Operation(summary = "Get User By Id")
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id){
		logger.info("Fetching User by Id");
		UserResponseDto userResponseDto=userService.getUserById(id);
		return new ResponseEntity<UserResponseDto>(userResponseDto,HttpStatus.OK);
		
	}
	
	@Operation(summary = "Fetch All User")
	@GetMapping()
	public ResponseEntity<PageResponse<UserResponseDto>> getAllUser(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		logger.info("Fetching All The Users");
		PageResponse<UserResponseDto> users = userService.getAllUser(page, size, sortBy, direction);

		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@Operation(summary = "Update Existing User")
	@PutMapping
	public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto userRequestDto){
		logger.info("Updating Existing User");
		UserResponseDto userResponseDto=userService.updateUser(userRequestDto);
		return new ResponseEntity<UserResponseDto>(userResponseDto,HttpStatus.OK);
		
	}
	
	@Operation(summary = "Delete User By User Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
		logger.info("Deleting  A  User");
		String  message=userService.deleteUser(id);
		return new ResponseEntity<>(message,HttpStatus.OK);
		
	}
	
}
