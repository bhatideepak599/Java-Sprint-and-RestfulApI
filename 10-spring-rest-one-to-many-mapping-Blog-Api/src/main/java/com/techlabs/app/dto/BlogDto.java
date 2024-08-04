package com.techlabs.app.dto;

import java.util.Date;
import java.util.List;

import com.techlabs.app.entity.Comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BlogDto {
	
	@NotNull
	private int id;
	@NotBlank()
	@Size(min = 2, max = 50)
	private String title;
	
	@NotBlank()
	private String category;
	
	@NotBlank()
	private String data;
	private Date date;
	
	@NotNull
	private boolean published;
	
	private List<CommentDto> commentsDto;
}
