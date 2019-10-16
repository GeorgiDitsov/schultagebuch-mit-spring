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

public class SchulerValidierungIT extends AbstraktEntityValidierungIT {

	@Autowired
	private Schuler schuler;

	@Autowired
	private Benutzer benutzer;

	@Before
	public void initTestContext() {
		schuler = new Schuler();
		benutzer = new Benutzer();
		benutzer.setRolle(new Rolle());
		schuler.setBenutzer(benutzer);
	}

	@Test
	public void richtigSchulerBenutzerRolle() {
		// Given
		benutzer.getRolle().setName(RolleTyp.ROLLE_SCHULER);

		// When
		Set<ConstraintViolation<Schuler>> violations = getValidator().validateProperty(schuler, "benutzer");

		// Then
		assertThat(violations, is(empty()));
	}

	@Test
	public void falschSchulerBenutzerRolle() {
		// Given
		benutzer.getRolle().setName(RolleTyp.ROLLE_LEHRER);

		// When
		Set<ConstraintViolation<Schuler>> violations = getValidator().validateProperty(schuler, "benutzer");

		// Then
		assertThat(violations, is(not(empty())));
	}

}
