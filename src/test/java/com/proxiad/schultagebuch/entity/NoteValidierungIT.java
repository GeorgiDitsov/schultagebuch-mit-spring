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

	@Autowired
	private Klasse klasse;

	@Before
	public void initTestContext() {
		klasse = new Klasse(1L, 1, "a");
		Benutzer benutzer = new Benutzer(123L, "VasilVasilev", "password", new Rolle(1, RolleTyp.ROLLE_SCHULER));
		klasse.setSchulerSet(new HashSet<>());
		schuler = new Schuler(1L, "Vasil Vasilev", "0001010000", klasse, benutzer);
		Lehrer lehrer = new Lehrer(Long.MAX_VALUE, "Ivan Ivanov", "8008088888",
				new Benutzer(150L, "OtherBenutzerName", "validPass", new Rolle(2, RolleTyp.ROLLE_LEHRER)));
		lehrer.setSchulfachSet(new HashSet<>());
		Schulfach schulfach = new Schulfach(55L, "Math");
		lehrer.getSchulfachSet().add(schulfach);
		schulstunde = new Schulstunde(123L, klasse, schulfach, lehrer);
		note = new Note(14L, (byte) 6, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde);
	}

	@Test
	public void validRelationZwischenSchulerUndSchulstunde() {
		// Given
		klasse.getSchulerSet().add(schuler);

		// When
		Set<ConstraintViolation<Note>> violations = getValidator().validate(note);

		// Then
		assertThat(violations, is(empty()));
	}

	@Test
	public void keineRelationZwischenSchulerUndSchulstunde() {
		// Given
		klasse.getSchulerSet().clear();

		// When
		Set<ConstraintViolation<Note>> violations = getValidator().validate(note);

		// Then
		assertThat(violations, is(not(empty())));
	}

}
