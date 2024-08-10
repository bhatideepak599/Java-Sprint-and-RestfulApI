package com.techlabs.app.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

	private Long customerId;
	private boolean active;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	
	private List<Long> accountNumbersList;
	
}
