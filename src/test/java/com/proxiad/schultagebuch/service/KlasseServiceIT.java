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

import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.KlasseRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class KlasseServiceIT {

	@Mock
	private KlasseRepository repo;

	@InjectMocks
	private KlasseService service;

	@Test
	public void gefundenLeereListeDerKlasse() {
		// Given
		List<Klasse> leereListe = new ArrayList<>();
		when(repo.findAllByOrderByIdAsc()).thenReturn(leereListe);

		// When
		List<Klasse> listOfKlassen = service.findeAlle();

		// Then
		assertThat(listOfKlassen, is(emptyCollectionOf(Klasse.class)));
	}

	// Then
	@Test(expected = EntityNichtGefundenException.class)
	public void keineKlasseGefunden() {
		// Given
		Long id = Long.MAX_VALUE;
		when(repo.findById(id)).thenReturn(Optional.empty());

		// When
		service.finden(id);

	}

}
