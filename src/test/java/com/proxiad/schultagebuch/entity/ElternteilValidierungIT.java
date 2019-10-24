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
	private Schuler schuler;

	@Autowired
	private Benutzer benutzer;

	@Autowired
	private Rolle rolle;

	@Before
	public void initTestContext() {
		schuler = new Schuler(Long.MAX_VALUE, "Rosen Bachev", "0707070808", new Klasse(Long.MAX_VALUE, 6, "a"),
				new Benutzer(Long.MAX_VALUE, "benutzername", "passwort",
						new Rolle(Integer.MAX_VALUE, RolleTyp.ROLLE_SCHULER)));
		elternteil = new Elternteil();
		elternteil.setId(123L);
		Set<Schuler> kinder = new HashSet<>();
		kinder.add(schuler);
		elternteil.setKinder(kinder);
		rolle = new Rolle();
		benutzer = new Benutzer();
		benutzer.setId(Long.MAX_VALUE);
		benutzer.setBenutzerName("ValidBenutzername");
		benutzer.setPasswort("ValidPass");
	}

	@Test
	public void richtigElternteilBenutzerRolle() {
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
