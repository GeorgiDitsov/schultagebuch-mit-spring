package com.proxiad.schultagebuch.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.validator.constraint.BenutzerLehrerRolleContraint;

public class BenutzerLehrerRolleValidator implements ConstraintValidator<BenutzerLehrerRolleContraint, Benutzer> {

	@Override
	public boolean isValid(Benutzer benutzer, ConstraintValidatorContext context) {
		return benutzer.getRolle() != null && benutzer.getRolle().getKennzeichen().equals("Lehrer");
	}

}
