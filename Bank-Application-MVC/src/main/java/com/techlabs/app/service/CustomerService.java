package com.techlabs.app.service;


import com.techlabs.app.dto.CustomerRequest;
import com.techlabs.app.dto.CustomerResponseDto;
import com.techlabs.app.util.PageResponse;

import jakarta.validation.Valid;

public interface CustomerService {

	PageResponse<CustomerResponseDto> getAllCustomers(int page, int size, String sortBy, String direction);

	CustomerResponseDto updateCustomer(@Valid CustomerRequest customerRequest);

	CustomerResponseDto addCustomer(CustomerRequest customerRequest);

	CustomerResponseDto getCustomerById(Long id);

	String deleteCustomerById(Long id);

}
