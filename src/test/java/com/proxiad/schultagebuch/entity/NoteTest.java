package com.proxiad.schultagebuch.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxiad.schultagebuch.util.RolleTyp;

@SpringBootTest
public class NoteTest extends AbstraktEntityValidationTest {

	@Autowired
	private Note note;

	@Autowired
	private Schuler schuler;

	@Autowired
	private Schulstunde schulstunde;

	@Autowired
	private Klasse klasse;

	@Autowired
	private Schulfach schulfach;

	@Autowired
	private Benutzer benutzer;

	@Autowired
	private Rolle rolle;

	@Before
	public void initTestContext() {
		rolle = new Rolle(1, RolleTyp.ROLLE_SCHULER);
		benutzer = new Benutzer(149, "ValidBenutzerName", "validPass", rolle);
		klasse = new Klasse(121, 1, "z");
		schuler = new Schuler(1, "Vasil Vasilev", "0001010000", klasse, benutzer);
		schulfach = new Schulfach(55, "Math");
		schulstunde = new Schulstunde(123, klasse, schulfach, null);
	}

	@Test
	public void validRelationZwischenSchulerUndSchulstunde() {
		// Given
		note = new Note(14, (byte) 6, LocalDateTime.now(), schuler, schulstunde);

		// When
		Set<ConstraintViolation<Note>> violations = getValidator().validate(note);

		// Then
		assertThat(violations, is(empty()));
	}

	@Test
	public void keineRelationZwischenSchulerUndSchulstunde() {
		// Given
		schuler.setKlasse(new Klasse(321, 1, "a"));
		note = new Note(14, (byte) 6, LocalDateTime.now(), schuler, schulstunde);

		// When
		Set<ConstraintViolation<Note>> violations = getValidator().validate(note);

		// Then
		assertThat(violations, is(not(empty())));
	}

}
