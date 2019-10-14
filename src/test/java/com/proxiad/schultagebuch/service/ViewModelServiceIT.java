package com.proxiad.schultagebuch.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.entity.Rolle;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.util.RolleTyp;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ViewModelServiceIT {

	@Mock
	private NoteService noteService;

	@InjectMocks
	private ViewModelService viewModelService;

	@Test
	public void getEmptyListNoteViewModelBySchuler() {
		// Given
		Schuler schuler = new Schuler(1, "Ivan Ivanov", "0001010000", new Klasse(1, 11, "z"),
				new Benutzer(1, "ValidUsername", "validPass", new Rolle(1, RolleTyp.ROLLE_SCHULER)));
		Semester semester = new Semester(1, LocalDateTime.MIN, LocalDateTime.MAX);

		// When
		when(noteService.findeSchulerNoten(schuler, semester)).thenReturn(new ArrayList<>());

		// Then
		assertThat(viewModelService.getListNoteViewModelBySchuler(schuler, Locale.getDefault()), is(empty()));
	}

}
