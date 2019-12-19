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
public class SchulstundeValidierungIT extends AbstraktEntityValidierungIT {

	@Autowired
	private Schulstunde schulstunde;

	@Autowired
	private Schulfach schulfach;

	@Autowired
	private Benutzer benutzer;

	@Before
	public void initTestContext() {
		benutzer = new Benutzer();
		benutzer.setRolle(new Rolle(Integer.MAX_VALUE, RolleTyp.ROLLE_LEHRER));
		schulfach = new Schulfach(Long.MAX_VALUE, "Englisch");
		schulstunde = new Schulstunde();
		schulstunde.setId(Long.MAX_VALUE);
		schulstunde.setKlasse(new Klasse(Long.MAX_VALUE, 9, "b"));
		schulstunde.setSchulfach(new Schulfach(Long.MAX_VALUE, "Englisch"));
	}

	@Test
	public void validRelationZwischenLehrerUndSchulfach() {
		// Given
		Lehrer lehrer = new Lehrer(Long.MAX_VALUE, "Nikola Penkov", "8001010000", benutzer);
		lehrer.setSchulfachSet(new HashSet<>());
		lehrer.getSchulfachSet().add(schulfach);
		schulstunde.setLehrer(lehrer);

		// When
		Set<ConstraintViolation<Schulstunde>> violations = getValidator().validate(schulstunde);

		// Then
		assertThat(violations, is(empty()));
	}

	@Test
	public void keineRelationZwischenLehrerUndSchulfach() {
		// Given
		Lehrer lehrer = new Lehrer(Long.MAX_VALUE, "Stefan Popov", "7901010000", benutzer);
		lehrer.setSchulfachSet(new HashSet<>());
		lehrer.getSchulfachSet().add(new Schulfach(1L, "Deutsch"));
		schulstunde.setLehrer(lehrer);

		// When
		Set<ConstraintViolation<Schulstunde>> violations = getValidator().validate(schulstunde);

		// Then
		assertThat(violations, is(not(empty())));
	}

}
