package com.proxiad.schultagebuch.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.proxiad.schultagebuch.validator.constraint.PINConstraint;

public class PINValidator implements ConstraintValidator<PINConstraint, String> {

	private static final String PIN_REGEX = "^([0-9]{2}([0][1-9]|[1][0-2])([0][1-9]|[1-2][0-9]|[3][0-1])([0-9]){4})$";

	@Override
	public boolean isValid(String pin, ConstraintValidatorContext context) {
		return pin != null && pin.matches(PIN_REGEX) && pin.length() == 10;
	}

}
