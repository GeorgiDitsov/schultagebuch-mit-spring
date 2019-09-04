package com.proxiad.schultagebuch.validator.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.proxiad.schultagebuch.validator.SchulstundeValidator;

@Documented
@Constraint(validatedBy = SchulstundeValidator.class)
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface SchulstundeConstraint {

	String message() default "Keine beziehung zwischen den entitäten";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
