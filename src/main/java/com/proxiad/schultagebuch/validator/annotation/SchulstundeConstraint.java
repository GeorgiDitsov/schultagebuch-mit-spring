package com.proxiad.schultagebuch.validator.annotation;

import static java.lang.annotation.ElementType.TYPE;
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
@Target(TYPE)
public @interface SchulstundeConstraint {

	String message() default "Keine beziehung zwischen den entit\u00e4ten";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
