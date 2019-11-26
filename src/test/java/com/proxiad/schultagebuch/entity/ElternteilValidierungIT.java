package com.proxiad.schultagebuch.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxiad.schultagebuch.util.RolleTyp;

@SpringBootTest
public class ElternteilValidierungIT extends AbstraktEntityValidierungIT {

	@Autowired
	private Elternteil elternteil;

	@Autowired
	private Benutzer benutzer;

	@Autowired
	private Rolle rolle;

	@Before
	public void initTestContext() {
		elternteil = new Elternteil();
		rolle = new Rolle();
		benutzer = new Benutzer();
		benutzer.setId(Long.MAX_VALUE);
		benutzer.setBenutzername("ValidBenutzername");
		benutzer.setPasswort("ValidPass");
	}

	@Test
	public void elternteilKinderValidatorTest() {
		// Given
		Set<Schuler> kinder = new HashSet<>();
		kinder.add(new Schuler());
		elternteil.setKinder(kinder);

		// When
		Set<ConstraintViolation<Elternteil>> violations = getValidator().validateProperty(elternteil, "kinder");

		// Then
		assertThat(violations, is(empty()));
	}

	@Test
	public void elternteilMitOhneKinder() {
		// Given
		Set<Schuler> leereSetDerKinder = new HashSet<>();
		elternteil.setKinder(leereSetDerKinder);

		// When
		Set<ConstraintViolation<Elternteil>> violations = getValidator().validateProperty(elternteil, "kinder");

		// Then
		assertThat(violations, is(not(empty())));
	}

	@Test
	public void richtigeElternteilBenutzerRolle() {
		// Given
		rolle.setName(RolleTyp.ROLLE_ELTERNTEIL);
		benutzer.setRolle(rolle);
		elternteil.setBenutzer(benutzer);

		// When
		Set<ConstraintViolation<Elternteil>> violations = getValidator().validateProperty(elternteil, "benutzer");

		// Then
		assertThat(violations, is(empty()));
	}

	@Test
	public void falschElternteilBenutzerRolle() {
		// Given
		rolle.setName(RolleTyp.ROLLE_SCHULER);
		benutzer.setRolle(rolle);
		elternteil.setBenutzer(benutzer);

		// When
		Set<ConstraintViolation<Elternteil>> violations = getValidator().validateProperty(elternteil, "benutzer");

		// Then
		assertThat(violations, is(not(empty())));
	}

}
