package com.proxiad.schultagebuch.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
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
import org.springframework.context.MessageSource;

import com.proxiad.schultagebuch.entity.Schulfach;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.SchulfachRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SchulfachServiceIT {

	@Mock
	private SchulfachRepository repo;

	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private SchulfachService service;

	@Test
	public void findeAlleTest() {
		// Given
		List<Schulfach> listOfSchulfach = new ArrayList<>();
		listOfSchulfach.add(new Schulfach());
		listOfSchulfach.add(new Schulfach());
		listOfSchulfach.add(new Schulfach());

		// When
		when(repo.findAllByOrderByIdAsc()).thenReturn(listOfSchulfach);
		List<Schulfach> gefundenListe = service.findeAlle();

		// Then
		assertThat(gefundenListe, hasSize(equalTo(listOfSchulfach.size())));
	}

	@Test
	public void gefundenLeereListeDerSchulfaecher() {
		// Given
		when(repo.findAllByOrderByIdAsc()).thenReturn(new ArrayList<>());

		// When
		List<Schulfach> listOfSchulfaecher = service.findeAlle();

		// Then
		assertThat(listOfSchulfaecher, is(emptyCollectionOf(Schulfach.class)));
	}

	@Test
	public void findeSchulfachTest() {
		// Given
		Long id = Long.MAX_VALUE;
		Schulfach schulfach = new Schulfach();

		// When
		when(repo.findById(id)).thenReturn(Optional.of(schulfach));
		Schulfach gefundenSchulfach = service.finden(id);

		// Then
		assertThat(gefundenSchulfach, is(equalTo(schulfach)));
	}

	@Test(expected = EntityNichtGefundenException.class)
	public void keineSchulfachGefunden() {
		// Given
		Long id = Long.MAX_VALUE;

		// When
		when(repo.findById(id)).thenReturn(Optional.empty());

		// Then
		service.finden(id);
	}

}
