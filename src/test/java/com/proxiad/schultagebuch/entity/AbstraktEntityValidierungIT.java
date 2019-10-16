package com.proxiad.schultagebuch.entity;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstraktEntityValidierungIT {

	private static ValidatorFactory validatorFactory;
	private static Validator validator;

	@BeforeClass
	public static void validatorErstellen() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@AfterClass
	public static void close() {
		validatorFactory.close();
	}

	public static Validator getValidator() {
		return validator;
	}

}
