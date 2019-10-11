package com.proxiad.schultagebuch.util;

import javax.validation.ValidationException;

import org.springframework.validation.BindingResult;

public final class ValidierungUtils {

	private ValidierungUtils() {
		// nothing
	}

	public static void fehlerPruefen(final BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
		}
	}

}