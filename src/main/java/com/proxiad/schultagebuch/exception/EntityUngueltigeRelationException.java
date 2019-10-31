package com.proxiad.schultagebuch.exception;

public class EntityUngueltigeRelationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;

	public EntityUngueltigeRelationException() {
		super();
	}

	public EntityUngueltigeRelationException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
