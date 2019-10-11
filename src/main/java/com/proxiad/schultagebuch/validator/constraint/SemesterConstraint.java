package com.proxiad.schultagebuch.validator.constraint;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.proxiad.schultagebuch.validator.SemesterValidator;

@Documented
@Constraint(validatedBy = SemesterValidator.class)
@Retention(RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
public @interface SemesterConstraint {

	String message() default "{invalid.semester.dates}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
