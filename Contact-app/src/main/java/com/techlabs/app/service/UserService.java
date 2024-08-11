package com.techlabs.app.service;

import com.techlabs.app.dto.RegisterDTO;
import com.techlabs.app.dto.UserRequestDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.util.PageResponse;

public interface UserService {

	UserResponseDto createNewUser(RegisterDTO registerDto);

	UserResponseDto getUserById(Long id);

	PageResponse<UserResponseDto> getAllUser(int page, int size, String sortBy, String direction);

	UserResponseDto updateUser(UserRequestDto userRequestDto);
	
	String deleteUser(Long id);

}
