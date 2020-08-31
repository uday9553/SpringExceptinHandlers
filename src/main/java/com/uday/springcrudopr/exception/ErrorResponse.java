package com.uday.springcrudopr.exception;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ErrorResponse {

	private final LocalDateTime timeStamp = LocalDateTime.now();

	@NotNull
	@JsonProperty
	private String errorMessage;

	@JsonProperty
	private String status;

	@JsonProperty
	private String reason;

	@JsonProperty
	private String systemMessage;
	
	public ErrorResponse(String errorMessage, String status) {
		this.errorMessage = errorMessage;
		this.status = status;
	}

	public ErrorResponse(String errorMessage, String systemMessage, String status) {
		this.errorMessage = errorMessage;
		this.systemMessage = systemMessage;
		this.status = status;
	}
}
