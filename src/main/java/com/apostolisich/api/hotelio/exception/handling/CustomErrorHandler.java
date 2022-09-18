package com.apostolisich.api.hotelio.exception.handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.apostolisich.api.hotelio.exception.OfferNotFoundException;
import com.apostolisich.api.hotelio.exception.UnsupportedProviderOperationException;

@ControllerAdvice
public class CustomErrorHandler {
	
	@ExceptionHandler(UnsupportedProviderOperationException.class)
	public ResponseEntity<ErrorResponse> handleUnsupportedProviderOperationExceptions(Exception e) {
		HttpStatus badRequestHttpStatus = HttpStatus.BAD_REQUEST;
		ErrorResponse errorResponse = new ErrorResponse(badRequestHttpStatus, e.getMessage());
		
		return new ResponseEntity<>(errorResponse, badRequestHttpStatus);
	}
	
	@ExceptionHandler(OfferNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleOfferNotFoundExceptions(Exception e) {
		HttpStatus badRequestHttpStatus = HttpStatus.NOT_FOUND;
		ErrorResponse errorResponse = new ErrorResponse(badRequestHttpStatus, e.getMessage());
		
		return new ResponseEntity<>(errorResponse, badRequestHttpStatus);
	}

}
