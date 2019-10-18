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

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.repository.BenutzerRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BenutzerServiceIT {

	@Mock
	private BenutzerRepository repo;

	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private BenutzerService service;

	@Test
	public void keineBenutzerGefundenMitDiesemBenutzername() {
		// Given
		String benutzername = "benutzername";
		when(repo.findByBenutzerNameIgnoreCaseLikeOrderByIdAsc("%" + benutzername + "%")).thenReturn(new ArrayList<>());

		// When
		List<Benutzer> list = service.suchen(benutzername);

		// Then
		assertThat(list, is(emptyCollectionOf(Benutzer.class)));
	}

	@Test
	public void keineBenutzerGefunden() {
		// Given
		when(repo.findAllByOrderByIdAsc()).thenReturn(new ArrayList<>());

		// When
		List<Benutzer> list = service.findeAlle();

		// Then
		assertThat(list, is(emptyCollectionOf(Benutzer.class)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void keineBenutzerMitDieseId() {
		// Given
		int id = Integer.MAX_VALUE;

		// When
		when(repo.findById(id)).thenReturn(Optional.empty());

		// Then
		service.finden(id, Locale.getDefault());

	}

}
