package com.system.exception;

public class RequiredFieldEmptyException extends Exception {

	private static final long serialVersionUID = -1216144199586164670L;

	private String message;
	
	public RequiredFieldEmptyException() {
	}
	
	public RequiredFieldEmptyException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
