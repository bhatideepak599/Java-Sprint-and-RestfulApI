package com.techlabs.app.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDto {
	@NotNull
	private int id;
	
	@NotBlank()
	private String description;
	
	
}
