package com.techlabs.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
	@NotNull
	@NotBlank
    private String firstName;
	
	@NotNull
    private String lastName;
	
	@NotNull
	@NotBlank
    private String username;
	
	@NotNull
	@NotBlank
    private String password;
    
}
