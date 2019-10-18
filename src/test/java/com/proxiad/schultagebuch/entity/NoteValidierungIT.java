package com.proxiad.schultagebuch.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxiad.schultagebuch.util.RolleTyp;

@SpringBootTest
public class NoteValidierungIT extends AbstraktEntityValidierungIT {

	@Autowired
	private Note note;

	@Autowired
	private Schuler schuler;

	@Autowired
	private Schulstunde schulstunde;

	@Before
	public void initTestContext() {
		Klasse klasse = new Klasse(121, 1, "z");
		schuler = new Schuler(1, "Vasil Vasilev", "0001010000", klasse,
				new Benutzer(149, "ValidBenutzerName", "validPass", new Rolle(1, RolleTyp.ROLLE_SCHULER)));
		Schulfach schulfach = new Schulfach(55, "Math");
		Lehrer lehrer = new Lehrer(Integer.MAX_VALUE, "Ivan Ivanov", "8008088888",
				new Benutzer(150, "OtherBenutzerName", "validPass", new Rolle(2, RolleTyp.ROLLE_LEHRER)));
		lehrer.setSchulfachSet(new HashSet<>());
		lehrer.getSchulfachSet().add(schulfach);
		schulstunde = new Schulstunde(123, klasse, schulfach, lehrer);
	}

	@Test
	public void validRelationZwischenSchulerUndSchulstunde() {
		// Given
		note = new Note(14, (byte) 6, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde);

		// When
		Set<ConstraintViolation<Note>> violations = getValidator().validate(note);

		// Then
		assertThat(violations, is(empty()));
	}

	@Test
	public void keineRelationZwischenSchulerUndSchulstunde() {
		// Given
		schuler.setKlasse(new Klasse(321, 1, "a"));
		note = new Note(14, (byte) 6, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde);

		// When
		Set<ConstraintViolation<Note>> violations = getValidator().validate(note);

		// Then
		assertThat(violations, is(not(empty())));
	}

}
