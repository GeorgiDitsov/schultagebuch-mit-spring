package com.proxiad.schultagebuch.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.entity.Rolle;
import com.proxiad.schultagebuch.util.RolleTyp;
import com.proxiad.schultagebuch.validator.constraint.BenutzerElternteilRolleContraint;

public class BenutzerElternteilRolleValidator
		implements ConstraintValidator<BenutzerElternteilRolleContraint, Benutzer> {

	@Override
	public boolean isValid(Benutzer benutzer, ConstraintValidatorContext context) {
		Rolle rolle = benutzer.getRolle();
		return Objects.nonNull(rolle) && rolle.getName().equals(RolleTyp.ROLLE_ELTERNTEIL);
	}

}
