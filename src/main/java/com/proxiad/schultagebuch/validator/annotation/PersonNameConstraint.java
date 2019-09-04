package com.proxiad.schultagebuch.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.proxiad.schultagebuch.validator.PersonNameValidator;

@Documented
@Constraint(validatedBy = PersonNameValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface PersonNameConstraint {

	String message() default "Falsch person name";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
