package com.proxiad.schultagebuch.validator.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface BenutzerSchulerConstraint {

	String message() default "Die rolle des benutzers stimmt nicht \u00fcberein";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
