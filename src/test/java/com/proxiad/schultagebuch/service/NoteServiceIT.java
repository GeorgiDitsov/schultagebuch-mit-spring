package com.proxiad.schultagebuch.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.NoteRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class NoteServiceIT {

	@Mock
	private NoteRepository repo;

	@InjectMocks
	private NoteService service;

	// Then
	@Test(expected = EntityNichtGefundenException.class)
	public void keineNoteMitDieserId() {
		// Given
		Long id = Long.MAX_VALUE;
		when(repo.findById(id)).thenReturn(Optional.empty());

		// When
		service.finden(id);
	}

	@Test
	public void gefundenLeereListeDerSchulerNoten() {
		// Given
		Schuler schuler = new Schuler();
		Semester semester = new Semester();
		List<Note> leereListe = new ArrayList<>();
		when(repo.findBySchulerAndNoteUpdateDatumBetweenOrderByNoteUpdateDatumDesc(schuler,
				semester.getSemesterbeginn(), semester.getSemesterende())).thenReturn(leereListe);

		// When
		List<Note> gefundenListe = service.findeSchulerNoten(schuler, semester);

		// Then
		assertThat(gefundenListe, is(emptyCollectionOf(Note.class)));
	}

	@Test
	public void gefundenLeereListeDerSchulerNotenDurchSchulerUndSchulstunde() {
		// Given
		Schuler schuler = new Schuler();
		Schulstunde schulstunde = new Schulstunde();
		Semester semester = new Semester();
		List<Note> leereListe = new ArrayList<>();

		// When
		when(repo.findBySchulerAndSchulstundeAndNoteUpdateDatumBetween(schuler, schulstunde,
				semester.getSemesterbeginn(), semester.getSemesterende())).thenReturn(leereListe);
		List<Note> gefundenListe = service.findeSchulerNotenDurchSchulstunde(schuler, schulstunde, semester);

		// Then
		assertThat(gefundenListe, is(emptyCollectionOf(Note.class)));
	}

}
