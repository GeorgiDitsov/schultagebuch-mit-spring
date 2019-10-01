package com.proxiad.schultagebuch.controller;

import javax.validation.ValidationException;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javassist.tools.web.BadHttpRequest;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	private static final String DEFAULT_ERROR_VIEW = "error";
	private static final String ERROR_STATUS = "statusCode";
	private static final String ERROR_MESSAGE = "errorMessage";

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = { ValidationException.class, PSQLException.class, DataAccessException.class })
	public ModelAndView datenbankFehler(final RuntimeException exception) {
		return getDefaultErrorView(exception.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class, BadHttpRequest.class })
	public ModelAndView schlechteAnfrage(final RuntimeException exception) {
		return getDefaultErrorView(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value());
	}

	private ModelAndView getDefaultErrorView(final String errorMessage, final int statusCode) {
		ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
		mav.addObject(ERROR_MESSAGE, errorMessage);
		mav.addObject(ERROR_STATUS, statusCode);
		return mav;
	}

}
