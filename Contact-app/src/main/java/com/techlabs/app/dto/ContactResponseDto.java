package com.techlabs.app.dto;

import java.util.List;

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
public class ContactResponseDto {
	private Long contact_Id;

	private String firstName;

	private String lastName;

	private boolean isActive;

	private List<ContactDetailsDto> contactDetailDtoList;
}
