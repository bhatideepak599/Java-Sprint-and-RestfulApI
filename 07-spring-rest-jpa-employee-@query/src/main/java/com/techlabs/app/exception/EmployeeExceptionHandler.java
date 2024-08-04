package com.techlabs.app.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<EmployeeErrorResponse> handleException(EmployeeNotFoundException ex) {
		// EmployeeErrorResponse error= new EmployeeErrorResponse(HttpStatus.NOT_FOUND,
		// ex.getMessage(), System.currentTimeMillis());
		// return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
		EmployeeErrorResponse error = new EmployeeErrorResponse();

		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		error.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	  public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getFieldErrors()
	        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	  }
	@ExceptionHandler
	public ResponseEntity<EmployeeErrorResponse> handleException(Exception ex) {
		// EmployeeErrorResponse error= new EmployeeErrorResponse(HttpStatus.NOT_FOUND,
		// ex.getMessage(), System.currentTimeMillis());
		// return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
		EmployeeErrorResponse error = new EmployeeErrorResponse();

		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		error.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
}
