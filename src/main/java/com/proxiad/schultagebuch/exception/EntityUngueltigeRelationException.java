package com.proxiad.schultagebuch.exception;

public class EntityUngueltigeRelationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityUngueltigeRelationException() {
		super();
	}

	public EntityUngueltigeRelationException(String message) {
		super(message);
	}

}
