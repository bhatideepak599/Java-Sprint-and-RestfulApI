package com.techlabs.app.dto;

import java.util.List;
import java.util.Set;

import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.Role;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponseDto {

	@NotNull
	@NotBlank
	private Long user_id;

	@Nonnull
	@NotBlank
	private String firstName;

	private String lastName;

	@NotNull
	@NotBlank
	private String username;

	@NotBlank
	private boolean active;
	private List<ContactResponseDto> contactDtoList;

}
