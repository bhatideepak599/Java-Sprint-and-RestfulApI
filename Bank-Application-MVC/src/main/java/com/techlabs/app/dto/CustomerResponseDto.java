package com.techlabs.app.dto;

import java.util.List;

import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerResponseDto {
private Long customerId;
	
	@NotBlank
	@NotNull
	private String firstName;
	private String lastName;
	private boolean active;
	@Null
	@NotBlank
	private double totalBalance;
	private List<AccountResponse> accounts;
}
