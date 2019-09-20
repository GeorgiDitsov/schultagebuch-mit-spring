package com.proxiad.schultagebuch.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.proxiad.schultagebuch.validator.constraint.BenutzerPasswortConstraint;

public class BenutzerPasswortValidator implements ConstraintValidator<BenutzerPasswortConstraint, String> {

	private static final String PASSWORT_REGEX = "^(?=.*[a-zA-Z0-9])[a-zA-Z0-9]{5,10}$";

	@Override
	public boolean isValid(String passwort, ConstraintValidatorContext context) {
		return passwort != null && passwort.matches(PASSWORT_REGEX);
	}

}
