package com.techlabs.app.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techlabs.app.controller.AuthController;
import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.RegisterDto;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Admin;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.entity.Role;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.CustomerApiException;
import com.techlabs.app.repository.AdminRepository;
import com.techlabs.app.repository.CustomerRepository;
import com.techlabs.app.repository.RoleRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.security.JwtTokenProvider;

import io.jsonwebtoken.io.IOException;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	private CustomerRepository customerRepository;
	private AdminRepository adminRepository;
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
	private final String FOLDER_PATH = "D:\\SpringFolder\\Bank-Application-MVC\\documents\\";

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
			CustomerRepository customerRepository, AdminRepository adminRepository) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.customerRepository = customerRepository;
		this.adminRepository = adminRepository;
	}

	@Override
	public String login(LoginDto loginDto) {
		User user = userRepository.findByUsername(loginDto.getUsername()).get();
		if (user.isActive() == false) {
			throw new CustomerApiException(HttpStatus.BAD_REQUEST, "User doest not exists!.");
		}
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		return token;
	}

	@Override
	public String register(RegisterDto registerDto, String role, MultipartFile file) {

		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new CustomerApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
		}

//        if(userRepository.existsByEmail(registerDto.getEmail())){
//            throw new CustomerApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
//        }

		User user = new User();

		user.setFirstName(registerDto.getFirstName());
		user.setUsername(registerDto.getUsername());
		user.setLastName(registerDto.getLastName());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setActive(true);

		Admin admin = new Admin();
		if ("ROLE_ADMIN".equals(role)) {

			admin.setFirstName(registerDto.getFirstName());
			admin.setLastName(registerDto.getLastName());

			admin.setUser(user);
			user.setAdmin(admin);

		}
		Customer customer = new Customer();
		if ("ROLE_CUSTOMER".equals(role)) {

			customer.setFirstName(registerDto.getFirstName());
			customer.setLastName(registerDto.getLastName());
			List<Account> lst = new ArrayList<>();
			customer.setAccounts(lst);
			customer.setActive(true);
			customer.setTotalBalance(0);
			customer.setUser(user);
			user.setCustomer(customer);

		}
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(role).get();
		roles.add(userRole);
		user.setRoles(roles);
		
		userRepository.save(user);
		if ("ROLE_CUSTOMER".equals(role))
			customerRepository.save(customer);
		
	
		
		if ("ROLE_ADMIN".equals(role))
			adminRepository.save(admin);
		if ("ROLE_CUSTOMER".equals(role))
			customerRepository.save(customer);
		
		if (role.equals("ROLE_CUSTOMER")) {
			try {
				saveCustomerFile(file, customer.getCustomerId());
			} catch (IllegalStateException | java.io.IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "User Registered As " + role.substring(5) + " Successfully!.";
	}

	private void saveCustomerFile(MultipartFile file, Long customerId)
			throws IllegalStateException, java.io.IOException {
		try {
			String folderPath = FOLDER_PATH + customerId;
			File directory = new File(folderPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			String filePath = folderPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(filePath).toPath());
			logger.info("File saved at: {}", filePath);
		} catch (IOException e) {
			logger.error("Error saving file: {}", e.getMessage());
			throw new RuntimeException("File saving failed");
		}
	}
}
