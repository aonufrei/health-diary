package com.aonufrei.healthdiary.exceptions;

public class DataValidationException extends RuntimeException {

	public DataValidationException(String description) {
		super(description);
	}

	public DataValidationException(String description, Throwable cause) {
		super(description, cause);
	}

	public DataValidationException(Throwable cause) {
		super(cause);
	}

}
