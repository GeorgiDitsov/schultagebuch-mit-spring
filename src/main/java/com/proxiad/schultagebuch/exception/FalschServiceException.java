package com.proxiad.schultagebuch.exception;

public class FalschServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE = "invalid.service";

	public FalschServiceException() {
		super(DEFAULT_MESSAGE);
	}

	public FalschServiceException(String message) {
		super(message);
	}

}
