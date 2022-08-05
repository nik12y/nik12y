package com.idg.idgcore.coe.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.*;
import org.springframework.web.util.WebUtils;

import java.util.Date;

public class UrnGeneratorExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<UrnGeneratorError> handleAllExceptions(Exception ex, WebRequest request) {
		UrnGeneratorError exceptionResponse = new UrnGeneratorError(new Date(), ex.getMessage(),
				request.getDescription(false), 500);
		request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, RequestAttributes.SCOPE_REQUEST);
		return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UrnGeneratorException.class)
	public final ResponseEntity<UrnGeneratorError> handleUserStatusException(UrnGeneratorException ex,
			WebRequest request) {
		UrnGeneratorError exceptionResponse = new UrnGeneratorError(new Date(), ex.getMessage(),
				request.getDescription(false), 500);
		request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, RequestAttributes.SCOPE_REQUEST);
		return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
