package com.proxiad.schultagebuch.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.repository.NoteRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class NoteServiceIT {

	@Mock
	private NoteRepository repo;

	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private NoteService service;

	@Test(expected = IllegalArgumentException.class)
	public void keineNoteMitDieseId() {
		// Given
		Long id = Long.MAX_VALUE;

		// When
		when(repo.findById(id)).thenReturn(Optional.empty());

		// Then
		service.finden(id, Locale.getDefault());
	}

	@Test
	public void gefundenLeereListeDerSchulerNoten() {
		// Given
		Schuler schuler = new Schuler();
		Semester semester = new Semester();

		// When
		when(repo.findBySchulerAndNoteUpdateDatumBetweenOrderByNoteUpdateDatumDesc(schuler,
				semester.getSemesterbeginn(), semester.getSemesterende())).thenReturn(new ArrayList<>());
		List<Note> list = service.findeSchulerNoten(schuler, semester);

		// Then
		assertThat(list, is(emptyCollectionOf(Note.class)));
	}

	@Test
	public void gefundenLeereListeDerSchulerNotenDurch() {
		// Given
		Schuler schuler = new Schuler();
		Schulstunde schulstunde = new Schulstunde();
		Semester semester = new Semester();

		// When
		when(repo.findBySchulerAndSchulstundeAndNoteUpdateDatumBetween(schuler, schulstunde,
				semester.getSemesterbeginn(), semester.getSemesterende())).thenReturn(new ArrayList<>());
		List<Note> list = service.findeSchulerNotenDurchSchulstunde(schuler, schulstunde, semester);

		// Then
		assertThat(list, is(emptyCollectionOf(Note.class)));
	}

}
