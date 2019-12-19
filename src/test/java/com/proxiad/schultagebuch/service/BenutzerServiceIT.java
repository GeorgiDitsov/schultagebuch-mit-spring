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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.BenutzerRepository;
import com.proxiad.schultagebuch.util.SuchenUtils;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BenutzerServiceIT {

	private static final String BENUTZERNAME = "benutzername";

	@Mock
	private BenutzerRepository repo;

	@InjectMocks
	private BenutzerService service;

	@Test
	public void gefundenLeereListeDerBenutzer() {
		// Given
		when(repo.findByBenutzernameIgnoreCaseLikeOrderByIdAsc(SuchenUtils.suchenNach(BENUTZERNAME)))
				.thenReturn(new ArrayList<>());

		// When
		List<Benutzer> list = service.suchen(BENUTZERNAME);

		// Then
		assertThat(list, is(emptyCollectionOf(Benutzer.class)));
	}

	@Test(expected = UsernameNotFoundException.class)
	public void keineBenutzerGefundenDurchBenutzername() {
		// Given
		String benutzername = BENUTZERNAME;

		// When
		when(repo.findByBenutzername(benutzername)).thenReturn(Optional.empty());

		// Then
		service.findeDurchBenutzername(benutzername);
	}

	@Test
	public void keineBenutzerGefunden() {
		// Given
		List<Benutzer> leereListe = new ArrayList<>();

		// When
		when(repo.findAllByOrderByIdAsc()).thenReturn(leereListe);
		List<Benutzer> list = service.findeAlle();

		// Then
		assertThat(list, is(emptyCollectionOf(Benutzer.class)));
	}

	@Test(expected = EntityNichtGefundenException.class)
	public void keineBenutzerMitDieseId() {
		// Given
		Long id = Long.MAX_VALUE;

		// When
		when(repo.findById(id)).thenReturn(Optional.empty());

		// Then
		service.finden(id);
	}

}
