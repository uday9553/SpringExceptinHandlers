package com.uday.springcrudopr.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BaseException {
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String userMessage) {
		super(userMessage);
	}

	public EntityNotFoundException(String userMessage, String systemMessage, HttpStatus httpStatus) {
		super(userMessage, systemMessage, httpStatus);
	}

	public EntityNotFoundException(String userMessage, HttpStatus httpStatus, Throwable cause) {
		super(userMessage, httpStatus, cause);
	}

	public EntityNotFoundException(String userMessage, Throwable cause) {
		super(userMessage, cause);
	}

}
