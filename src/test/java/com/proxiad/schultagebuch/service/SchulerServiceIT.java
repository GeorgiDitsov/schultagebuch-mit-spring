package com.proxiad.schultagebuch.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulfach;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.repository.SchulerRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SchulerServiceIT {

	@Mock
	private SchulerRepository repo;

	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private SchulerService service;

	@Test
	public void gefundenLeereListeDerSchuler() {
		// Given
		when(repo.findAllByOrderByIdAsc()).thenReturn(new ArrayList<>());

		// When
		List<Schuler> listOfSchuler = service.findeAlle();

		// Then
		assertThat(listOfSchuler, is(emptyCollectionOf(Schuler.class)));
	}

	@Test
	public void ohneSchulernImKlasse() {
		// Given
		Klasse klasse = new Klasse();

		// When
		when(repo.findByKlasseOrderByIdAsc(klasse)).thenReturn(new ArrayList<>());
		List<Schuler> listOfSchuler = service.findeAlleSchulernImKlasse(klasse);

		// Then
		assertThat(listOfSchuler, is(emptyCollectionOf(Schuler.class)));
	}

	@Test
	public void findeAlleSchulernImKlasseTest() {
		// Given
		Klasse klasse = new Klasse();
		List<Schuler> schulernImKlasseList = new ArrayList<>();
		schulernImKlasseList.add(new Schuler(145L, "Stefan Popov", "1010101010", new Klasse(), new Benutzer()));
		schulernImKlasseList.add(new Schuler(149L, "Nikola Peev", "1111111111", new Klasse(), new Benutzer()));
		schulernImKlasseList.add(new Schuler(200L, "Alex Alexandrov", "1212121212", new Klasse(), new Benutzer()));

		// When
		when(repo.findByKlasseOrderByIdAsc(klasse)).thenReturn(schulernImKlasseList);
		List<Schuler> listOfSchuler = service.findeAlleSchulernImKlasse(klasse);

		// Then
		assertThat(listOfSchuler, hasSize(equalTo(3)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void keineSchulerGefunden() {
		// Given
		Long id = Long.MAX_VALUE;

		// When
		when(repo.findById(id)).thenReturn(Optional.empty());

		// Then
		service.finden(id, Locale.getDefault());
	}

	@Test
	public void findeElternteilKind() {
		// Given
		Schuler kind = new Schuler(1L, "Pencho Nikolov", "1010101010", new Klasse(), new Benutzer());
		Set<Schuler> kinder = new HashSet<>();
		kinder.add(kind);
		Elternteil elternteil = new Elternteil();
		elternteil.setKinder(kinder);

		// When
		when(repo.findById(1L)).thenReturn(Optional.of(kind));
		Schuler schuler = service.findeElternteilKind(kind.getId(), elternteil, Locale.getDefault());

		// Then
		assertThat(schuler, samePropertyValuesAs(kind));
	}

	@Test(expected = IllegalArgumentException.class)
	public void eltenteilMitOhneKind() {
		// Given
		Long schulerId = Long.MAX_VALUE;

		// When
		when(repo.findById(schulerId)).thenReturn(Optional.empty());

		// Then
		service.findeElternteilKind(schulerId, new Elternteil(), Locale.getDefault());
	}

	@Test
	public void findeDurchSchulstundeTest() {
		// Given
		Klasse klasse = new Klasse();
		Schuler schuler = new Schuler(111L, "Ivan Klekov", "1010101010", klasse, new Benutzer());
		Set<Schuler> schulerSet = new HashSet<>();
		schulerSet.add(schuler);
		klasse.setSchulerSet(schulerSet);
		Schulstunde schulstunde = new Schulstunde(132L, klasse, new Schulfach(), new Lehrer());

		// When
		when(repo.findById(schuler.getId())).thenReturn(Optional.of(schuler));
		Schuler gefundenSchuler = service.findeDurchSchulstunde(schuler.getId(), schulstunde, Locale.getDefault());

		// Then
		assertThat(gefundenSchuler, samePropertyValuesAs(schuler));
	}

	@Test(expected = IllegalArgumentException.class)
	public void keineSchulerGefundenDurchSchulstunde() {
		// Given
		Long schulerId = Long.MAX_VALUE;

		// When
		when(repo.findById(schulerId)).thenReturn(Optional.empty());

		// Then
		service.findeDurchSchulstunde(schulerId, new Schulstunde(), Locale.getDefault());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void keineSchulerGefundenDurchBenutzername() {
		// Given
		String benutzename = "ExampleBenutzename";

		// When
		when(repo.findByBenutzerBenutzerName(benutzename)).thenReturn(Optional.empty());

		// Then
		service.findeDurchBenutzerName(benutzename, Locale.getDefault());
	}

}