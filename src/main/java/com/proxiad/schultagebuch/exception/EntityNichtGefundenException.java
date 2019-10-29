package com.proxiad.schultagebuch.exception;

public class EntityNichtGefundenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;
	private Object[] args;

	public EntityNichtGefundenException(String message, Object[] args) {
		this.message = message;
		this.args = args;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public Object[] getArgs() {
		return args;
	}

}
