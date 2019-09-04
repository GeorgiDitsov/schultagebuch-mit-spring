package com.proxiad.schultagebuch.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.proxiad.schultagebuch.validator.annotation.KlasseNameConstraint;

public class KlasseNameValidator implements ConstraintValidator<KlasseNameConstraint, String> {

	@Override
	public boolean isValid(String klasseName, ConstraintValidatorContext context) {
		return klasseName != null && klasseName.matches("^([1-9][a-z]|[1][0-2][a-z])$");
	}

}
