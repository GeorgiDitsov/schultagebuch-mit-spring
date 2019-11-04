package com.proxiad.schultagebuch.controller;

import java.util.Locale;

import javax.validation.ValidationException;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.exception.EntityUngueltigeRelationException;
import com.proxiad.schultagebuch.exception.FalschServiceException;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	private static final String DEFAULT_ERROR_VIEW = "error";
	private static final String ERROR_STATUS = "statusCode";
	private static final String ERROR_MESSAGE = "errorMessage";

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = { PSQLException.class, DataAccessException.class })
	public ModelAndView datenbankFehler(final RuntimeException exception) {
		int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
		return getDefaultErrorView(exception.getLocalizedMessage(), statusCode);
	}

	@ExceptionHandler(ValidationException.class)
	public ModelAndView validierungFehler(final ValidationException exception) {
		int statusCode = HttpStatus.BAD_REQUEST.value();
		return getDefaultErrorView(exception.getLocalizedMessage(), statusCode);
	}

	@ExceptionHandler(EntityNichtGefundenException.class)
	public ModelAndView schulerNichtGefunden(final EntityNichtGefundenException exception, final Locale locale) {
		String errorMessage = messageSource.getMessage(exception.getMessage(), exception.getArgs(), locale);
		int statusCode = HttpStatus.BAD_REQUEST.value();
		return getDefaultErrorView(errorMessage, statusCode);
	}

	@ExceptionHandler(EntityUngueltigeRelationException.class)
	public ModelAndView entityUngueltigeRelation(final EntityUngueltigeRelationException exception,
			final Locale locale) {
		String errorMessage = messageSource.getMessage(exception.getMessage(), null, locale);
		int statusCode = HttpStatus.BAD_REQUEST.value();
		return getDefaultErrorView(errorMessage, statusCode);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ModelAndView benutzernameNichtGefunden(final UsernameNotFoundException exception) {
		int statusCode = HttpStatus.BAD_REQUEST.value();
		return getDefaultErrorView(exception.getLocalizedMessage(), statusCode);
	}

	@ExceptionHandler(FalschServiceException.class)
	public ModelAndView falschService(final FalschServiceException exception, final Locale locale) {
		int statusCode = HttpStatus.BAD_REQUEST.value();
		String errorMessage = messageSource.getMessage(exception.getMessage(), null, locale);
		return getDefaultErrorView(errorMessage, statusCode);
	}

	private ModelAndView getDefaultErrorView(final String errorMessage, final int statusCode) {
		ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
		mav.addObject(ERROR_MESSAGE, errorMessage);
		mav.addObject(ERROR_STATUS, statusCode);
		return mav;
	}

}
