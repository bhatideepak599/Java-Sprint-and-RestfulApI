package com.techlabs.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;

public class ResponseDto {

	private int id;

	@NotBlank()
	@Size(min = 2, max = 50)
	private String name;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String designation;

	public ResponseDto() {
		super();

	}

	public ResponseDto(int id, @NotBlank @Size(min = 2, max = 50) String name, @NotBlank @Email String email,
			@NotBlank String designation) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.designation = designation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Override
	public String toString() {
		return "ResponseDto [Id=" + id + ", Name=" + name + ", Email=" + email + ", Designation=" + designation + "]";
	}

}
