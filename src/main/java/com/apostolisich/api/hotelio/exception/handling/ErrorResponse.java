package com.apostolisich.api.hotelio.exception.handling;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class that represents custom error responses when an exception is thrown and
 * handled internally.
 */
public class ErrorResponse {
	
	private LocalDateTime timestamp;
	@JsonProperty("statusCode")
	private int httpStatusCode;
	@JsonProperty("statusMessage")
	private String httpStatusMessage;
	@JsonProperty("error")
	private String errorMessage;
	
	public ErrorResponse(HttpStatus httpStatus, String errorMessage) {
		this.timestamp = LocalDateTime.now();
		this.httpStatusCode = httpStatus.value();
		this.httpStatusMessage = httpStatus.getReasonPhrase();
		this.errorMessage = errorMessage;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public String getHttpStatusMessage() {
		return httpStatusMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
