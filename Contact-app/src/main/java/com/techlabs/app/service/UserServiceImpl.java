package com.techlabs.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.RegisterDTO;
import com.techlabs.app.dto.UserRequestDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.Role;
import com.techlabs.app.entity.User;

import com.techlabs.app.exception.UserNotFoundException;
import com.techlabs.app.mapper.Mapper;
import com.techlabs.app.repository.RoleRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.util.PageResponse;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private Mapper mapper;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;

	}

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserResponseDto createNewUser(RegisterDTO registerDto) {
		User user = mapper.registerToUser(registerDto);

		Optional<Role> byName = roleRepository.findByName("ROLE_STAFF");
		if (byName.isEmpty()) {
			logger.error("ROLE NOT FOUND");
			throw new RuntimeException("ROLE NOT FOUND ");
		}
		Set<Role> roles = new HashSet<>();
		roles.add(byName.get());
		user.setRoles(roles);
		userRepository.save(user);
		UserResponseDto userResponseDto = mapper.userToUserResponseDto(user);

		return userResponseDto;
	}

	@Override
	public UserResponseDto getUserById(Long id) {
		Optional<User> byId = userRepository.findById(id);
		if (byId.isEmpty() || byId.get().isActive() == false) {
			logger.error("No User Found With Id: " + id);
			throw new UserNotFoundException("No User Found With Id: " + id);
		}
		User user = byId.get();
		return mapper.userToUserResponseDto(user);
	}

	@Override
	public PageResponse<UserResponseDto> getAllUser(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<User> all = userRepository.findAll(pageable);

		if (all.getContent().isEmpty()) {
			logger.error("No Users Found");
			throw new UserNotFoundException("No Users Found");
		}

		List<UserResponseDto> users = all.getContent().stream().map(user -> mapper.userToUserResponseDto(user))
				.toList();

		return new PageResponse<UserResponseDto>(users, all.getNumber(), all.getNumberOfElements(),
				all.getTotalElements(), all.getTotalPages(), all.isLast());

	}

	@Override
	public UserResponseDto updateUser(UserRequestDto userRequestDto) {
		Long id = userRequestDto.getId();
		Optional<User> byId = userRepository.findById(id);
		if (byId.isEmpty() ) {
			logger.error("No User Found With Id: " + id);
			throw new UserNotFoundException("No User Found With Id: " + id);
		}
		User user = byId.get();

		if (userRequestDto.getFirstName() != null || userRequestDto.getFirstName() != "")
			user.setFirstName(userRequestDto.getFirstName());

		if (userRequestDto.getLastName() != null || userRequestDto.getLastName() != "") {
			user.setLastName(userRequestDto.getLastName());
		}
		if (userRequestDto.getPassword() != null || userRequestDto.getPassword() != "")
			user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

		if (user.isActive() == false) {
			user.setActive(true);

			List<Contact> contacts = user.getContacts();
			for (Contact contact : contacts) {
				contact.setActive(true);
			}
			user.setContacts(contacts);
		}
		User save = userRepository.save(user);

		return mapper.userToUserResponseDto(save);
	}

	@Override
	public String deleteUser(Long id) {

		Optional<User> byId = userRepository.findById(id);
		if (byId.isEmpty() || byId.get().isActive() == false) {
			logger.error("No User Found With Id: " + id);
			throw new UserNotFoundException("No User Found With Id: " + id);
		}
		User user = byId.get();

		user.setActive(false);
		List<Contact> contacts = user.getContacts();
		for (Contact contact : contacts) {
			contact.setActive(false);
		}
		user.setContacts(contacts);
		userRepository.save(user);

		return "User Deleted SuccessFully";
	}

}
