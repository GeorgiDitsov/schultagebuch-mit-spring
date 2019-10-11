package com.proxiad.schultagebuch.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.validator.constraint.SemesterConstraint;

public class SemesterValidator implements ConstraintValidator<SemesterConstraint, Semester> {

	@Override
	public boolean isValid(Semester semester, ConstraintValidatorContext context) {
		return semester.getSemesterbeginn().isBefore(semester.getSemesterende());
	}

}
