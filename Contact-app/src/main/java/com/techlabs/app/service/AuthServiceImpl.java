package com.techlabs.app.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.RegisterDTO;
import com.techlabs.app.entity.Role;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.APIException;
import com.techlabs.app.repository.RoleRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public String login(LoginDto loginDto) {

		Optional<User> byUsername = userRepository.findByUsername(loginDto.getUsernameOrEmail());
		
		if (byUsername.isEmpty() || byUsername.get().isActive() == false) {
			throw new APIException(HttpStatus.BAD_REQUEST, "User doest not exists!.");
		}
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		return token;
	}

	@Override
	public String register(RegisterDTO registerDTO, String newRole) {
		if (userRepository.existsUserByUsername(registerDTO.getUsername())) {
			throw new APIException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
		}

		/*
		 * if(userRepository.existsUserByUsername(registerDTO.getUsername())){ throw new
		 * APIException(HttpStatus.BAD_REQUEST, "Email is already exists!."); }
		 */

		User user = new User();
		user.setFirstName(registerDTO.getFirstName());
		user.setLastName(registerDTO.getLastName());
		user.setUsername(registerDTO.getUsername());
		user.setAdmin(false);

		if ("ROLE_ADMIN".equalsIgnoreCase(newRole)) {
			user.setAdmin(true);
		}
		user.setActive(true);
		user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(newRole).get();
		roles.add(userRole);
		user.setRoles(roles);

		userRepository.save(user);

		return "User Registered Successfully";

	}
}
