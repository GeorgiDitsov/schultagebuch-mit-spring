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

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.entity.Schulfach;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.exception.EntityUngueltigeRelationException;
import com.proxiad.schultagebuch.repository.SchulstundeRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SchulstundeServiceIT {

	@Mock
	private SchulstundeRepository repo;

	@InjectMocks
	private SchulstundeService service;

	@Test
	public void gefundenLeereListeDerSchulstunde() {
		// Given
		List<Schulstunde> leereListe = new ArrayList<>();
		when(repo.findAllByOrderByKlasseIdAscIdAsc()).thenReturn(leereListe);

		// When
		List<Schulstunde> gefundenListe = service.findeAlle();

		// Then
		assertThat(gefundenListe, is(emptyCollectionOf(Schulstunde.class)));
	}

	// Then
	@Test(expected = EntityNichtGefundenException.class)
	public void keineSchulstundeGefunden() {
		// Given
		Long id = Long.MAX_VALUE;
		when(repo.findById(id)).thenReturn(Optional.empty());

		// When
		service.finden(id);
	}

	@Test
	public void schulstundeFuerDieseLehrerGefunden() {
		// Given
		Long id = Long.MAX_VALUE;
		Lehrer lehrer = new Lehrer(1L, "Stefan Stefanov", "1010101010", new Benutzer());
		Schulstunde schulstunde = new Schulstunde(id, new Klasse(), new Schulfach(), lehrer);
		when(repo.findById(id)).thenReturn(Optional.of(schulstunde));

		// When
		Schulstunde gefundenSchulstunde = service.findeLehrerSchulstunde(id, lehrer);

		// Then
		assertThat(gefundenSchulstunde, is(equalTo(schulstunde)));
	}

	// Then
	@Test(expected = EntityUngueltigeRelationException.class)
	public void keineSchulstundeFuerDieseLehrerGefunden() {
		// Given
		Long id = Long.MAX_VALUE;
		Schulstunde schulstunde = new Schulstunde(id, new Klasse(), new Schulfach(),
				new Lehrer(1L, "Stefan Stefanov", "1010101010", new Benutzer()));
		when(repo.findById(id)).thenReturn(Optional.of(schulstunde));

		// When
		service.findeLehrerSchulstunde(id, new Lehrer());
	}

	@Test
	public void findeDurchLehrerTest() {
		// Given
		Lehrer lehrer = new Lehrer();
		List<Schulstunde> listOfSchulstunden = new ArrayList<>();
		listOfSchulstunden.add(new Schulstunde());
		listOfSchulstunden.add(new Schulstunde());
		listOfSchulstunden.add(new Schulstunde());
		when(repo.findByLehrerOrderByKlasseIdAscIdAsc(lehrer)).thenReturn(listOfSchulstunden);

		// When
		List<Schulstunde> gefundenListe = service.findeDurchLehrer(lehrer);

		// Then
		assertThat(gefundenListe, hasSize(equalTo(listOfSchulstunden.size())));
	}

	@Test
	public void gefundenLeereListeDerSchulstundeDurchLehrer() {
		// Given
		Lehrer lehrer = new Lehrer();
		List<Schulstunde> leereListe = new ArrayList<>();
		when(repo.findByLehrerOrderByKlasseIdAscIdAsc(lehrer)).thenReturn(leereListe);

		// When
		List<Schulstunde> gefundenListe = service.findeDurchLehrer(lehrer);

		// Then
		assertThat(gefundenListe, is(emptyCollectionOf(Schulstunde.class)));
	}

	@Test
	public void findeDurchKlasseTest() {
		// Given
		Klasse klasse = new Klasse();
		List<Schulstunde> listOfSchulstunden = new ArrayList<>();
		listOfSchulstunden.add(new Schulstunde());
		listOfSchulstunden.add(new Schulstunde());
		listOfSchulstunden.add(new Schulstunde());
		when(repo.findByKlasseOrderByIdAsc(klasse)).thenReturn(listOfSchulstunden);

		// When
		List<Schulstunde> gefundenListe = service.findeDurchKlasse(klasse);

		// Then
		assertThat(gefundenListe, hasSize(equalTo(listOfSchulstunden.size())));
	}

	@Test
	public void gefundenLeereListeDerSchulstundeDurchKlasse() {
		// Given
		Klasse klasse = new Klasse();
		List<Schulstunde> leereListe = new ArrayList<>();
		when(repo.findByKlasseOrderByIdAsc(klasse)).thenReturn(leereListe);

		// When
		List<Schulstunde> gefundenListe = service.findeDurchKlasse(klasse);

		// Then
		assertThat(gefundenListe, is(emptyCollectionOf(Schulstunde.class)));
	}

}
