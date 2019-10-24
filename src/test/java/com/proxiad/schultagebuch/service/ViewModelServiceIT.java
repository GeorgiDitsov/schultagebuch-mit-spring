package com.proxiad.schultagebuch.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
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

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Rolle;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulfach;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.util.RolleTyp;
import com.proxiad.schultagebuch.view.KindViewModel;
import com.proxiad.schultagebuch.view.NoteViewModel;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ViewModelServiceIT {

	@Autowired
	private Klasse klasse;

	@Autowired
	private Benutzer benutzer;

	@Autowired
	private Rolle rolle;

	@Mock
	private NoteService noteService;

	@Mock
	private SemesterService semesterService;

	@InjectMocks
	private ViewModelService viewModelService;

	@Before
	public void initTestContext() {
		klasse = new Klasse(1L, 11, "z");
		rolle = new Rolle(1, RolleTyp.ROLLE_SCHULER);
		benutzer = new Benutzer(1L, "ValidUsername", "validPass", rolle);
	}

	@Test
	public void getEmptyListNoteViewModelDurchSchuler() {
		// Given
		Schuler schuler = new Schuler(1L, "Ivan Ivanov", "0001010000", klasse, benutzer);
		Semester semester = new Semester(1, LocalDateTime.MIN, LocalDateTime.MAX);
		when(semesterService.findeAktuelleSemester(Locale.getDefault())).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(new ArrayList<>());

		// When
		List<NoteViewModel> list = viewModelService.getListNoteViewModelBySchuler(schuler, Locale.getDefault());

		// Then
		assertThat(list, is(emptyCollectionOf(NoteViewModel.class)));
	}

	@Test
	public void getLeereListNoteViewModelDurchSchulerUndSchulstunde() {
		// Given
		Schuler schuler = new Schuler(2L, "Stanislav Pelov", "1010101010", klasse, benutzer);
		Schulstunde schulstunde = new Schulstunde(11L, klasse, new Schulfach(1L, "Math"), new Lehrer());
		Semester semester = new Semester(1, LocalDateTime.MIN, LocalDateTime.MAX);
		when(semesterService.findeAktuelleSemester(Locale.getDefault())).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(new ArrayList<>());

		// When
		List<NoteViewModel> list = viewModelService.getListNoteViewModelBySchulerUndSchulstunde(schuler, schulstunde,
				Locale.getDefault());

		// Then
		assertThat(list, is(emptyCollectionOf(NoteViewModel.class)));
	}

	@Test
	public void getListNoteViewModelDurchSchulerUndSchulstundeMitFalschRelation() {
		// Given
		Schuler schuler = new Schuler();
		schuler.setKlasse(klasse);
		Schulstunde schulstunde = new Schulstunde(24L, new Klasse(), new Schulfach(), new Lehrer());
		Schulstunde andereSchulstunde = new Schulstunde(258L, new Klasse(), new Schulfach(), new Lehrer());
		List<Note> notenList = new ArrayList<>();
		notenList.add(new Note(1L, (byte) 5, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));
		Semester semester = new Semester(1, LocalDateTime.MIN, LocalDateTime.MAX);
		when(semesterService.findeAktuelleSemester(Locale.getDefault())).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(notenList);

		// When
		List<NoteViewModel> list = viewModelService.getListNoteViewModelBySchulerUndSchulstunde(schuler,
				andereSchulstunde, Locale.getDefault());

		// Then
		assertThat(list, is(emptyCollectionOf(NoteViewModel.class)));
	}

	@Test
	public void elternteilMitOhneKinderKinderViewModelle() {
		// Given
		Elternteil elternteil = new Elternteil();
		Set<Schuler> ohneKinderSet = new HashSet<>();
		elternteil.setKinder(ohneKinderSet);

		// When
		List<KindViewModel> kinder = viewModelService.getKinderViewModelle(elternteil, Locale.getDefault());

		// Then
		assertThat(kinder, is(emptyCollectionOf(KindViewModel.class)));
	}

}
