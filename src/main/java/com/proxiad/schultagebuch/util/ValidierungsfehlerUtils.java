package com.proxiad.schultagebuch.util;

import javax.validation.ValidationException;

import org.springframework.validation.BindingResult;

public class ValidierungsfehlerUtils {

	private ValidierungsfehlerUtils() {
		// nothing
	}

	public static void fehlerPruefen(final BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
	}

}
