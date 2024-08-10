package com.techlabs.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import com.techlabs.app.dto.CustomerRequest;
import com.techlabs.app.dto.CustomerResponseDto;

import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Customer;

import com.techlabs.app.entity.Role;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.CustomerApiException;
import com.techlabs.app.exception.CustomerNotFoundException;

import com.techlabs.app.mapping.MappingBetweenDtoAndEntity;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.CustomerRepository;
import com.techlabs.app.repository.RoleRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.util.PageResponse;

@Service

public class CustomerServiceImpl implements CustomerService {
	private CustomerRepository customerRepository;

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private AccountRepository accountRepository;
	private MappingBetweenDtoAndEntity mapper;

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository,
			RoleRepository roleRepository, AccountRepository accountRepository, MappingBetweenDtoAndEntity mapper) {
		super();
		this.customerRepository = customerRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.accountRepository = accountRepository;
		this.mapper = mapper;
	}

	@Override
	public PageResponse<CustomerResponseDto> getAllCustomers(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Customer> all = customerRepository.findAll(pageable);

		if (all.getContent().isEmpty()) {
			logger.error("No Customers Found");
			throw new CustomerNotFoundException("No Customers Found");
		}

		List<CustomerResponseDto> customers = mapper.getCustomerResponseList(all.getContent());

		return new PageResponse<CustomerResponseDto>(customers, all.getNumber(), all.getNumberOfElements(),
				all.getTotalElements(), all.getTotalPages(), all.isLast());

	}

	@Override
	public CustomerResponseDto updateCustomer(CustomerRequest customerRequest) {

		Optional<Customer> byId = customerRepository.findById(customerRequest.getCustomerId());
		if (byId.isEmpty()) {
			logger.error("No Customer Found With Id : " + customerRequest.getCustomerId());
			throw new CustomerNotFoundException("No Customer Found With Id : " + customerRequest.getCustomerId());
		}

		Customer customer = byId.get();
		if (customerRequest.getFirstName() != null || customerRequest.getFirstName().length() != 0)
			customer.setFirstName(customerRequest.getFirstName());
		if (customerRequest.getLastName() != null || customerRequest.getLastName().length() != 0)
			customer.setLastName(customerRequest.getLastName());

		if (customer.isActive() == false) {
			List<Account> accounts = customer.getAccounts();
			List<Long> accountNumbers = customerRequest.getAccountNumbersList();

			List<Account> matchedAccounts = accounts.stream()
					.filter(account -> accountNumbers.contains(account.getAccountNumber()))
					.collect(Collectors.toList());

			for (Account matchedAccount : matchedAccounts) {
				accounts.remove(matchedAccount);
				matchedAccount.setActive(true);
				matchedAccount.setBalance(1000);
				customer.setTotalBalance(1000 + customer.getTotalBalance());
				matchedAccount.setCustomer(customer);
				accounts.add(matchedAccount);
			}
			customer.setAccounts(accounts);
			accountRepository.saveAll(accounts);
			customerRepository.save(customer);
		}

		Customer save = customerRepository.save(customer);
		return mapper.customerToCustomerResponseDto(customer);

	}

	@Override
	public CustomerResponseDto addCustomer(CustomerRequest customerRequest) {

		if (userRepository.existsByUsername(customerRequest.getUsername())) {
			logger.error("Username is already exists!.");
			throw new CustomerApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
		}
		customerRequest.setActive(true);
		User user = mapper.customerRequestToUser(customerRequest);
		Customer customer = mapper.customerRequestToCustomer(customerRequest);

		customer.setUser(user);
		
		user.setCustomer(customer);

		Optional<Role> byName = roleRepository.findByName("ROLE_CUSTOMER");
		if (byName.isEmpty()) {
			logger.error("ROLE NOT FOUND");
			throw new RuntimeException("ROLE NOT FOUND ");
		}
		Set<Role> roles = new HashSet<>();
		roles.add(byName.get());
		user.setRoles(roles);
		userRepository.save(user);
		customerRepository.save(customer);

		return mapper.customerToCustomerResponseDto(customer);

	}

	@Override
	public CustomerResponseDto getCustomerById(Long id) {
		Optional<Customer> byId = customerRepository.findById(id);
		if (byId.isEmpty()) {
			logger.error("No Customer Found With Id : " + id);
			throw new CustomerNotFoundException("No Customer Found With Id : " + id);
		}
		Customer customer = byId.get();

		return mapper.customerToCustomerResponseDto(customer);
	}

	@Override
	public String deleteCustomerById(Long id) {
		Optional<Customer> byId = customerRepository.findById(id);
		if (byId.isEmpty() || byId.get().isActive() == false) {
			logger.error("No Customer Found With Id : " + id);
			throw new CustomerNotFoundException("No Customer Found With Id : " + id);
		}

		Customer customer = byId.get();
		User user = customer.getUser();
		user.setActive(false);

//		Role userRole = roleRepository.findByName("ROLE_CUSTOMER").get();
//
//		user.getRoles().remove(userRole);

		customer.setActive(false);
		customer.setTotalBalance(0);
		List<Account> accounts = customer.getAccounts();
		for (Account account : accounts) {
			account.setActive(false);
			account.setCustomer(customer);
			account.setBalance(0);
		}
		accountRepository.saveAll(accounts);
		customerRepository.save(customer);

		userRepository.save(user);
		return "Customer Deleted Successfully.";
	}

}
