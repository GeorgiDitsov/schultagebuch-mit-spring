package com.proxiad.schultagebuch.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.entity.Rolle;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulfach;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.util.RolleTyp;
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
		klasse = new Klasse(1, 11, "z");
		rolle = new Rolle(1, RolleTyp.ROLLE_SCHULER);
		benutzer = new Benutzer(1, "ValidUsername", "validPass", rolle);
	}

	@Test
	public void getEmptyListNoteViewModelBySchuler() {
		// Given
		Schuler schuler = new Schuler(1, "Ivan Ivanov", "0001010000", klasse, benutzer);
		Semester semester = new Semester(1, LocalDateTime.MIN, LocalDateTime.MAX);

		// When
		when(semesterService.findeAktuelleSemester(Locale.getDefault())).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(new ArrayList<>());
		List<NoteViewModel> list = viewModelService.getListNoteViewModelBySchuler(schuler, Locale.getDefault());

		// Then
		assertThat(list, is(emptyCollectionOf(NoteViewModel.class)));
	}

	@Test
	public void getEmptyListNoteViewModelBySchulerUndSchulstunde() {
		// Given
		Schuler schuler = new Schuler(2, "Stanislav Pelov", "1010101010", klasse, benutzer);
		Schulstunde schulstunde = new Schulstunde(11, klasse, new Schulfach(1, "Math"), new Lehrer());
		Semester semester = new Semester(1, LocalDateTime.MIN, LocalDateTime.MAX);

		// When
		when(semesterService.findeAktuelleSemester(Locale.getDefault())).thenReturn(semester);
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(new ArrayList<>());
		List<NoteViewModel> list = viewModelService.getListNoteViewModelBySchulerUndSchulstunde(schuler, schulstunde,
				Locale.getDefault());

		// Then
		assertThat(list, is(emptyCollectionOf(NoteViewModel.class)));
	}

}
