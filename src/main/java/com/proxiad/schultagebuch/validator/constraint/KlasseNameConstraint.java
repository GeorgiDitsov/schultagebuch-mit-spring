package com.proxiad.schultagebuch.validator.constraint;

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
@Target(ElementType.PARAMETER)
public @interface KlasseNameConstraint {

	String message() default "{invalid.class.name}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
