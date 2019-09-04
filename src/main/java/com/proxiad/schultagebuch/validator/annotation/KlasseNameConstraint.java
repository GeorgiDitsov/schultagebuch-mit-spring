package com.proxiad.schultagebuch.validator.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.proxiad.schultagebuch.validator.KlasseNameValidator;

@Documented
@Constraint(validatedBy = KlasseNameValidator.class)
@Retention(RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface KlasseNameConstraint {

	String message() default "Falsch klasse name";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
