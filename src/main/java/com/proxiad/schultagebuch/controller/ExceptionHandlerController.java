package com.proxiad.schultagebuch.controller;

import javax.validation.ValidationException;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	private static final String DEFAULT_ERROR_VIEW = "error";
	private static final String ERROR_STATUS = "statusCode";
	private static final String EXCEPTION = "exception";

	@ExceptionHandler(value = { ValidationException.class, PSQLException.class, DataAccessException.class })
	protected ModelAndView datenbankFehler(final RuntimeException exception) {
		return getDefaultErrorView(exception, HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ModelAndView schlechteAnfrage(final RuntimeException exception) {
		return getDefaultErrorView(exception, HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(value = { AuthenticationException.class })
	protected ModelAndView nichtAutorisiert(final AuthenticationException exception) {
		return getDefaultErrorView(exception, HttpStatus.UNAUTHORIZED.value());
	}

	private ModelAndView getDefaultErrorView(final Exception exception, final int statusCode) {
		ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
		mav.addObject(EXCEPTION, exception);
		mav.addObject(ERROR_STATUS, statusCode);
		return mav;
	}

}
