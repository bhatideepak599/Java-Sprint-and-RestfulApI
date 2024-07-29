package com.techlabs.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RequestDto {

	private int id;

	@NotBlank()
	@Size(min = 2, max = 50)
	private String name;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String designation;

	@NotNull
	private double salary;

	@NotNull
	private boolean active;

	public RequestDto() {
		super();

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

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "RequestDto [id=" + id + ", name=" + name + ", email=" + email + ", designation=" + designation
				+ ", salary=" + salary + ", active=" + active + "]";
	}

}
