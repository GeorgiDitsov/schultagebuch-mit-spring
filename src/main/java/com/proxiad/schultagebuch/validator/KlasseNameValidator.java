package com.proxiad.schultagebuch.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.proxiad.schultagebuch.validator.constraint.KlasseNameConstraint;

public class KlasseNameValidator implements ConstraintValidator<KlasseNameConstraint, String> {

	private static final String KLASSE_NAME_REGEX = "^([1-9][a-z]|[1][0-2][a-z])$";

	@Override
	public boolean isValid(String klasseName, ConstraintValidatorContext context) {
		return klasseName != null && klasseName.matches(KLASSE_NAME_REGEX);
	}

}
