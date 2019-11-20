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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulfach;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.view.KindViewModel;
import com.proxiad.schultagebuch.view.NoteViewModel;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ViewModelServiceIT {

	@Mock
	private NoteService noteService;

	@Mock
	private SemesterService semesterService;

	@InjectMocks
	private ViewModelService viewModelService;

	@Test
	public void getListeDerNoteViewModelleDurchSchulerTest() {
		// Given
		Schuler schuler = new Schuler();
		Semester semester = new Semester(1, LocalDateTime.MIN, LocalDateTime.MAX);
		List<Note> listOfNoten = new ArrayList<>();
		Schulstunde schulstunde = new Schulstunde(23L, new Klasse(), new Schulfach(), new Lehrer());
		listOfNoten.add(new Note(32L, (byte) 6, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));
		listOfNoten.add(new Note(33L, (byte) 2, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));
		listOfNoten.add(new Note(34L, (byte) 3, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));

		// When
		when(semesterService.findeAktuelleSemester()).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(listOfNoten);
		List<NoteViewModel> listOfNoteViewModelle = viewModelService.getListeDerNoteViewModelleDurchSchuler(schuler,
				Locale.getDefault());

		// Then
		assertThat(listOfNoteViewModelle, hasSize(equalTo(listOfNoten.size())));
	}

	@Test
	public void getLeereListeDerNoteViewModelleDurchSchuler() {
		// Given
		Schuler schuler = new Schuler();
		Semester semester = new Semester(1, LocalDateTime.MIN, LocalDateTime.MAX);

		// When
		when(semesterService.findeAktuelleSemester()).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(new ArrayList<>());
		List<NoteViewModel> listOfNoteViewModelle = viewModelService.getListeDerNoteViewModelleDurchSchuler(schuler,
				Locale.getDefault());

		// Then
		assertThat(listOfNoteViewModelle, is(emptyCollectionOf(NoteViewModel.class)));
	}

	@Test
	public void getListeDerNoteViewModelleDurchSchulerUndSchulstundeTest() {
		// Given
		Schuler schuler = new Schuler();
		Semester semester = new Semester(1, LocalDateTime.MIN, LocalDateTime.MAX);
		List<Note> listOfNoten = new ArrayList<>();
		Schulstunde schulstunde = new Schulstunde(23L, new Klasse(), new Schulfach(), new Lehrer());
		listOfNoten.add(new Note(32L, (byte) 6, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));
		listOfNoten.add(new Note(33L, (byte) 2, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));
		listOfNoten.add(new Note(34L, (byte) 3, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));
		when(semesterService.findeAktuelleSemester()).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(listOfNoten);

		// When
		List<NoteViewModel> listOfNoteViewModels = viewModelService
				.getListeDerNoteViewModelleDurchSchulerUndSchulstunde(schuler, schulstunde, Locale.getDefault());

		// Then
		assertThat(listOfNoteViewModels, hasSize(equalTo(listOfNoten.size())));
	}

	@Test
	public void getLeereListeDerNoteViewModelleDurchSchulerUndSchulstunde() {
		// Given
		Schuler schuler = new Schuler();
		Schulstunde schulstunde = new Schulstunde();
		Semester semester = new Semester(1, LocalDateTime.MIN, LocalDateTime.MAX);

		// When
		when(semesterService.findeAktuelleSemester()).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(new ArrayList<>());
		List<NoteViewModel> list = viewModelService.getListeDerNoteViewModelleDurchSchulerUndSchulstunde(schuler,
				schulstunde, Locale.getDefault());

		// Then
		assertThat(list, is(emptyCollectionOf(NoteViewModel.class)));
	}

	@Test
	public void getListNoteViewModelDurchSchulerUndSchulstundeMitFalschRelation() {
		// Given
		Schuler schuler = new Schuler();
		Schulstunde schulstunde = new Schulstunde();
		schulstunde.setId(124L);
		Schulstunde andereSchulstunde = new Schulstunde();
		andereSchulstunde.setId(111L);
		Semester semester = new Semester(1, LocalDateTime.MIN, LocalDateTime.MAX);
		List<Note> notenList = new ArrayList<>();
		notenList.add(new Note(1L, (byte) 5, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));

		// When
		when(semesterService.findeAktuelleSemester()).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(notenList);
		List<NoteViewModel> list = viewModelService.getListeDerNoteViewModelleDurchSchulerUndSchulstunde(schuler,
				andereSchulstunde, Locale.getDefault());

		// Then
		assertThat(list, is(emptyCollectionOf(NoteViewModel.class)));
	}

	@Test
	public void elternteilMitOhneKinder() {
		// Given
		Elternteil elternteil = new Elternteil();
		Set<Schuler> ohneKinderSet = new HashSet<>();
		elternteil.setKinder(ohneKinderSet);

		// When
		List<KindViewModel> kinder = viewModelService.getListeDerKinderViewModelleDurchElternteil(elternteil,
				Locale.getDefault());

		// Then
		assertThat(kinder, is(emptyCollectionOf(KindViewModel.class)));
	}

}
