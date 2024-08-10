package com.techlabs.app.dto;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.techlabs.app.entity.Course;
import com.techlabs.app.entity.Student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentDto {
	private int id;

	@NotBlank
	private String name;

	@Email
	private String email;

	private List<CourseDto> courses = new ArrayList<>();
	
	
}
