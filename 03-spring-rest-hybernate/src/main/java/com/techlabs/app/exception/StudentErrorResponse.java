package com.techlabs.app.exception;

import java.time.LocalDateTime;

public class StudentErrorResponse {
	private int status;
	private String message;
	private LocalDateTime timestamp;
	
	public StudentErrorResponse(int notFound, String message, LocalDateTime timestamp) {
		super();
		this.status = notFound;
		this.message = message;
		this.timestamp = timestamp;
	}
	public StudentErrorResponse() {
		
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int i) {
		this.status = i;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime l) {
		this.timestamp = l;
	}
	
}
