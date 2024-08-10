package com.techlabs.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.techlabs.app.dto.CustomerRequest;
import com.techlabs.app.dto.CustomerResponseDto;

import com.techlabs.app.service.CustomerService;
import com.techlabs.app.util.PageResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private CustomerService customerService;
	private static final Logger logger=LoggerFactory.getLogger(CustomerController.class);
	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}
	
	@Operation(summary = "Fetch All Customers ")
	@GetMapping()
	public ResponseEntity<PageResponse<CustomerResponseDto>> getAllCustomers(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size,
			@RequestParam(name = "sortBy", defaultValue = "customerId") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		logger.info("Fetching All The Customers");
		PageResponse<CustomerResponseDto> customers = customerService.getAllCustomers(page, size, sortBy, direction);

		return new ResponseEntity<>(customers, HttpStatus.OK);
	}
	
	@Operation(summary = "Add A New Customer")
	@PostMapping
	public ResponseEntity<CustomerResponseDto> addCustomer(@RequestBody CustomerRequest customerRequest) {
		
		logger.info("Adding A Customer");
		CustomerResponseDto customer = customerService.addCustomer(customerRequest);

		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@Operation(summary = "Update A  Customer")
	@PutMapping
	public ResponseEntity<CustomerResponseDto> updateCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
		logger.info("Updating A Customer");
		CustomerResponseDto customer = customerService.updateCustomer(customerRequest);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@Operation(summary = "Fetch Customer By Id")
	@GetMapping("/{id}")
	public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable("id") Long id) {
		
		logger.info("Fetch Customer By Id");
		CustomerResponseDto customer = customerService.getCustomerById(id);

		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@Operation(summary = "Delete Customer By Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable("id") Long id) {
		logger.info("Deleting A Customer with Customer Id");
		String message = customerService.deleteCustomerById(id);

		return new ResponseEntity<>(message, HttpStatus.OK);
	}

}
