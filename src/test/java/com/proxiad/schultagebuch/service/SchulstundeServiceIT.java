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

import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.repository.SchulstundeRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SchulstundeServiceIT {

	@Mock
	private SchulstundeRepository repo;

	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private SchulstundeService service;

	@Test
	public void gefundenLeereListeDerSchulstunde() {
		// Given
		List<Schulstunde> leereListe = new ArrayList<>();

		// When
		when(repo.findAllByOrderByKlasseIdAscIdAsc()).thenReturn(leereListe);
		List<Schulstunde> gefundenListe = service.findeAlle();

		// Then
		assertThat(gefundenListe, is(emptyCollectionOf(Schulstunde.class)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void keineSchulstundeGefunden() {
		// Given
		Long id = Long.MAX_VALUE;

		// When
		when(repo.findById(id)).thenReturn(Optional.empty());

		// Then
		service.finden(id, Locale.getDefault());
	}

}
