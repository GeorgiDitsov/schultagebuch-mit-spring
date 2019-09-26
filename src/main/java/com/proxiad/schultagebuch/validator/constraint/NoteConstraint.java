package com.proxiad.schultagebuch.validator.constraint;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.proxiad.schultagebuch.validator.NoteValidator;

@Documented
@Constraint(validatedBy = NoteValidator.class)
@Retention(RUNTIME)
@Target({ ElementType.TYPE, ElementType.PARAMETER, ElementType.CONSTRUCTOR })
public @interface NoteConstraint {

	String message() default "{invalid.student.course.relation}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
