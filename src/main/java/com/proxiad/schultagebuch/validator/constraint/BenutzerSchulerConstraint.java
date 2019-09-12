package com.proxiad.schultagebuch.validator.constraint;

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

	String message() default "{invalid.student.user.role}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
