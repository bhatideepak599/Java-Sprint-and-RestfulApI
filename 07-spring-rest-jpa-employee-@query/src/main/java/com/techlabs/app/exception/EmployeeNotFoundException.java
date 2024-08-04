package com.techlabs.app.exception;

public class EmployeeNotFoundException extends RuntimeException {
	public EmployeeNotFoundException(String s) {
		super(s);
	}
}
