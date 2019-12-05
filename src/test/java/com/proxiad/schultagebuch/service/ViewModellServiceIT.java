package com.proxiad.schultagebuch.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulfach;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.view.KindViewModell;
import com.proxiad.schultagebuch.view.NoteViewModell;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ViewModellServiceIT {

	@Autowired
	private Schuler schuler;

	@Autowired
	private Schulstunde schulstunde;

	@Autowired
	private Semester semester;

	@Mock
	private NoteService noteService;

	@Mock
	private SemesterService semesterService;

	@InjectMocks
	private ViewModellService viewModelService;

	@Before
	public void initTestContext() {
		schuler = new Schuler();
		semester = new Semester(1, LocalDateTime.MIN, LocalDateTime.MAX);
		schulstunde = new Schulstunde(23L, new Klasse(), new Schulfach(), new Lehrer());

	}

	@Test
	public void getListeDerNoteViewModelleDurchSchulerTest() {
		// Given
		List<Note> listOfNoten = new ArrayList<>();
		listOfNoten.add(new Note(32L, (byte) 6, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));
		listOfNoten.add(new Note(33L, (byte) 2, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));
		listOfNoten.add(new Note(34L, (byte) 3, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));

		// When
		when(semesterService.findeAktuelleSemester()).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(listOfNoten);
		List<NoteViewModell> listOfNoteViewModelle = viewModelService.getListeDerNoteViewModelleDurchSchuler(schuler,
				Locale.getDefault());

		// Then
		assertThat(listOfNoteViewModelle, hasSize(equalTo(listOfNoten.size())));
	}

	@Test
	public void getLeereListeDerNoteViewModelleDurchSchuler() {
		// Given
		List<Note> leereListe = new ArrayList<>();

		// When
		when(semesterService.findeAktuelleSemester()).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(leereListe);
		List<NoteViewModell> listOfNoteViewModelle = viewModelService.getListeDerNoteViewModelleDurchSchuler(schuler,
				Locale.getDefault());

		// Then
		assertThat(listOfNoteViewModelle, is(emptyCollectionOf(NoteViewModell.class)));
	}

	@Test
	public void getListeDerNoteViewModelleDurchSchulerUndSchulstundeTest() {
		// Given
		List<Note> listOfNoten = new ArrayList<>();
		listOfNoten.add(new Note(32L, (byte) 6, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));
		listOfNoten.add(new Note(33L, (byte) 2, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));
		listOfNoten.add(new Note(34L, (byte) 3, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));
		when(semesterService.findeAktuelleSemester()).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(listOfNoten);

		// When
		List<NoteViewModell> listOfNoteViewModels = viewModelService
				.getListeDerNoteViewModelleDurchSchulerUndSchulstunde(schuler, schulstunde, Locale.getDefault());

		// Then
		assertThat(listOfNoteViewModels, hasSize(equalTo(listOfNoten.size())));
	}

	@Test
	public void getLeereListeDerNoteViewModelleDurchSchulerUndSchulstunde() {
		// Given
		List<Note> leereListe = new ArrayList<>();

		// When
		when(semesterService.findeAktuelleSemester()).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(leereListe);
		List<NoteViewModell> list = viewModelService.getListeDerNoteViewModelleDurchSchulerUndSchulstunde(schuler,
				schulstunde, Locale.getDefault());

		// Then
		assertThat(list, is(emptyCollectionOf(NoteViewModell.class)));
	}

	@Test
	public void getListNoteViewModelDurchSchulerUndSchulstundeMitFalschRelation() {
		// Given
		Schulstunde andereSchulstunde = new Schulstunde();
		andereSchulstunde.setId(111L);
		List<Note> notenList = new ArrayList<>();
		notenList.add(new Note(1L, (byte) 5, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));

		// When
		when(semesterService.findeAktuelleSemester()).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(notenList);
		List<NoteViewModell> list = viewModelService.getListeDerNoteViewModelleDurchSchulerUndSchulstunde(schuler,
				andereSchulstunde, Locale.getDefault());

		// Then
		assertThat(list, is(emptyCollectionOf(NoteViewModell.class)));
	}

	@Test
	public void elternteilMitOhneKinder() {
		// Given
		Elternteil elternteil = new Elternteil();
		Set<Schuler> ohneKinderSet = new HashSet<>();
		elternteil.setKinder(ohneKinderSet);

		// When
		List<KindViewModell> listOfKindViewModell = viewModelService.getListeDerKinderViewModelleDurchElternteil(elternteil,
				Locale.getDefault());

		// Then
		assertThat(listOfKindViewModell, is(emptyCollectionOf(KindViewModell.class)));
	}

}
