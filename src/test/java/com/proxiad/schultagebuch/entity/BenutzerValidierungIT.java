package com.proxiad.schultagebuch.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxiad.schultagebuch.util.RolleTyp;

@SpringBootTest
public class BenutzerValidierungIT extends AbstraktEntityValidierungIT {

	@Autowired
	private Benutzer benutzer;

	@Autowired
	private Rolle rolle;

	@Before
	public void initTestContext() {
		rolle = new Rolle(1, RolleTyp.ROLLE_LEHRER);
		benutzer = new Benutzer();
		benutzer.setId(Integer.MAX_VALUE);
	}

	@Test
	public void richtigBenutzernameUndPasswort() {
		// Given
		benutzer.setBenutzerName("ValidBenutzername123");
		benutzer.setPasswort("ValidPass1");
		benutzer.setRolle(rolle);

		// When
		Set<ConstraintViolation<Benutzer>> violations = getValidator().validate(benutzer);

		// Then
		assertThat(violations, is(empty()));

	}

	@Test
	public void falschBenutzernameUndPasswort() {
		// Given
		benutzer.setBenutzerName("_InvalidBenutzername");
		benutzer.setPasswort("invalidPasswort");

		// When
		Set<ConstraintViolation<Benutzer>> violations = getValidator().validate(benutzer);

		// Then
		assertThat(violations, is(not(empty())));
	}

}
