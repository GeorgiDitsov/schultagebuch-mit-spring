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

import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.LehrerRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class LehrerServiceIT {

	@Mock
	private LehrerRepository repo;

	@InjectMocks
	private LehrerService service;

	@Test
	public void gefundenLeereListeDerLehrer() {
		// Given
		List<Lehrer> leereListe = new ArrayList<>();

		// When
		when(repo.findAllByOrderByIdAsc()).thenReturn(leereListe);
		List<Lehrer> gefundenListe = service.findeAlle();

		// Then
		assertThat(gefundenListe, is(emptyCollectionOf(Lehrer.class)));

	}

	@Test(expected = EntityNichtGefundenException.class)
	public void keineLehrerGefunden() {
		// Given
		Long id = Long.MAX_VALUE;

		// When
		when(repo.findById(id)).thenReturn(Optional.empty());

		// Then
		service.finden(id);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void keineLehrerGefundenDurchBenutzername() {
		// Given
		String benutzername = "ABenutzername";

		// When
		when(repo.findByBenutzerBenutzername(benutzername)).thenReturn(Optional.empty());

		// Then
		service.findeDurchBenutzername(benutzername);
	}

}
