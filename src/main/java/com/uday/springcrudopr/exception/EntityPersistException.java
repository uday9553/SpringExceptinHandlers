package com.uday.springcrudopr.exception;

import org.springframework.http.HttpStatus;

public class EntityPersistException  extends BaseException{
	private static final long serialVersionUID = -5030558261871830109L;

	public EntityPersistException(String userMessage) {
		super(userMessage);
	}
	public EntityPersistException(String userMessage, String systemMessage, HttpStatus httpStatus) {
		super(userMessage, systemMessage, httpStatus);
	}

	public EntityPersistException(String userMessage, HttpStatus httpStatus, Throwable cause) {
		super(userMessage, httpStatus, cause);
	}

	public EntityPersistException(String userMessage, Throwable cause) {
		super(userMessage, cause);
	}

}
