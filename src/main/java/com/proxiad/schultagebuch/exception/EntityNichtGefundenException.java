package com.proxiad.schultagebuch.exception;

public class EntityNichtGefundenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Object[] args;

	public EntityNichtGefundenException() {
		super();
	}

	public EntityNichtGefundenException(String message, Object[] args) {
		super(message);
		this.args = args;
	}

	public Object[] getArgs() {
		return args;
	}

}
