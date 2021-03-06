package com.proxiad.schultagebuch.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.proxiad.schultagebuch.validator.constraint.PersonNameConstraint;

public class PersonNameValidator implements ConstraintValidator<PersonNameConstraint, String> {

	private static final String PERSON_NAME_REGEX = "^([A-Z][a-z]+\\s[A-Z][a-z]+)$";

	@Override
	public boolean isValid(String personName, ConstraintValidatorContext context) {
		return Objects.nonNull(personName) && personName.matches(PERSON_NAME_REGEX);
	}

}
