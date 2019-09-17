package com.proxiad.schultagebuch.validator.constraint;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.proxiad.schultagebuch.validator.BenutzerLehrerRolleValidator;

@Documented
@Constraint(validatedBy = BenutzerLehrerRolleValidator.class)
@Retention(RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface BenutzerLehrerRolleContraint {

	String message() default "{invalid.teacher.user.role}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
