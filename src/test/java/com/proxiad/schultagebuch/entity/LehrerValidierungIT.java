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

import com.proxiad.schultagebuch.util.RolleTyp;

public class LehrerValidierungIT extends AbstraktEntityValidierungIT {

	@Autowired
	private Lehrer lehrer;

	@Autowired
	private Benutzer benutzer;

	@Before
	public void initTestContext() {
		lehrer = new Lehrer();
		benutzer = new Benutzer();
		benutzer.setRolle(new Rolle());
		lehrer.setBenutzer(benutzer);
	}

	@Test
	public void richtigLehrerBenutzerRolle() {
		// Given
		benutzer.getRolle().setName(RolleTyp.ROLLE_LEHRER);

		// When
		Set<ConstraintViolation<Lehrer>> violations = getValidator().validateProperty(lehrer, "benutzer");

		// Then
		assertThat(violations, is(empty()));
	}

	@Test
	public void falschLehrerBenutzerRolle() {
		// Given
		benutzer.getRolle().setName(RolleTyp.ROLLE_ELTERNTEIL);

		// When
		Set<ConstraintViolation<Lehrer>> violations = getValidator().validateProperty(lehrer, "benutzer");

		// Then
		assertThat(violations, is(not(empty())));
	}

}
