package com.proxiad.schultagebuch.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.validator.constraint.SchulstundeConstraint;

public class SchulstundeValidator implements ConstraintValidator<SchulstundeConstraint, Schulstunde> {

	@Override
	public boolean isValid(Schulstunde schulstunde, ConstraintValidatorContext context) {
		return Optional.ofNullable(schulstunde.getLehrer()).isPresent()
				? (schulstunde.getLehrer().getSchulfachSet().contains(schulstunde.getSchulfach()) ? true : false)
				: true;
	}

}
