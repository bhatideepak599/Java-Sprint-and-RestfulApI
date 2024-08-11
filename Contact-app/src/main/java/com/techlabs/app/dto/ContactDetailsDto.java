package com.techlabs.app.dto;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
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
public class ContactDetailsDto {
	
	private Long contact_detail_id;

	@NonNull
	@NotBlank
	private String type;

	@NonNull
	@NotBlank
	private String value;

}
