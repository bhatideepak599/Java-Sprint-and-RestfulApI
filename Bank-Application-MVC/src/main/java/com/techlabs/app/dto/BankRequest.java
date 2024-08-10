package com.techlabs.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class BankRequest {
	
	private Long bankId;
	@NotBlank
	@NotNull
	private String fullName;
	private boolean active;
	@NotBlank
	@NotNull
	private String abbreviation;
}
