package com.aonufrei.healthdiary.controllers.rest;

import com.aonufrei.healthdiary.exceptions.DataValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({DataValidationException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> errorHandler(DataValidationException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
