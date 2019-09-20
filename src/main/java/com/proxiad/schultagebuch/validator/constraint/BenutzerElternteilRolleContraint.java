package com.proxiad.schultagebuch.validator.constraint;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.proxiad.schultagebuch.validator.BenutzerElternteilRolleValidator;

@Documented
@Constraint(validatedBy = { BenutzerElternteilRolleValidator.class })
@Retention(RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface BenutzerElternteilRolleContraint {

	String message() default "{invalid.parent.user.role}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
