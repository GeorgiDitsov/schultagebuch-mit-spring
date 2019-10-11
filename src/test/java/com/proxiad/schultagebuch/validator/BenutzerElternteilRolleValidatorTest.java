package com.proxiad.schultagebuch.validator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Rolle;
import com.proxiad.schultagebuch.util.RolleTyp;

@SpringBootTest
public class BenutzerElternteilRolleValidatorTest {

	@Autowired
	private Elternteil elternteil;

	@Autowired
	private Benutzer benutzer;

	@Autowired
	private Rolle rolle;

	private Validator validator;

	@Before
	public void initTestContext() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		elternteil = new Elternteil();
		benutzer = new Benutzer();
		rolle = new Rolle();
	}

	@Test
	public void istRichtig() {
		// Given
		rolle.setName(RolleTyp.ROLLE_ELTERNTEIL);
		benutzer.setRolle(rolle);
		elternteil.setBenutzer(benutzer);

		// When
		Set<ConstraintViolation<Elternteil>> violations = validator.validateProperty(elternteil, "benutzer");

		// Then
		assertThat(violations, is(empty()));
	}

	@Test
	public void istFalsch() {
		// Given
		rolle.setName(RolleTyp.ROLLE_SCHULER);
		benutzer.setRolle(rolle);
		elternteil.setBenutzer(benutzer);

		// When
		Set<ConstraintViolation<Elternteil>> violations = validator.validateProperty(elternteil, "benutzer");

		// Then
		assertThat(violations, is(not(empty())));
	}

}
