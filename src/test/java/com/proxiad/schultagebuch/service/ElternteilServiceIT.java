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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.ElternteilRepository;
import com.proxiad.schultagebuch.util.SuchenUtils;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ElternteilServiceIT {

	private static final String ELTERNTEIL_NAME = "Stefan Stefanov";

	@Mock
	private ElternteilRepository repo;

	@InjectMocks
	private ElternteilService service;

	@Test
	public void gefundenListeDerEltern() {
		// Given
		List<Elternteil> listWithThreeEltern = new ArrayList<>();
		listWithThreeEltern.add(new Elternteil());
		listWithThreeEltern.add(new Elternteil());
		listWithThreeEltern.add(new Elternteil());
		when(repo.findAllByOrderByIdAsc()).thenReturn(listWithThreeEltern);

		// When
		List<Elternteil> gefundenListe = service.findeAlle();

		// Then
		assertThat(gefundenListe, hasSize(equalTo(listWithThreeEltern.size())));
	}

	@Test
	public void gefundenLeereListeDerEltern() {
		// Given
		List<Elternteil> leereListe = new ArrayList<>();
		when(repo.findAllByOrderByIdAsc()).thenReturn(leereListe);

		// When
		List<Elternteil> gefundenListe = service.findeAlle();

		// Then
		assertThat(gefundenListe, is(emptyCollectionOf(Elternteil.class)));
	}

	@Test
	public void gefundenLeereListeDerElternDurchName() {
		// Given
		List<Elternteil> leereListe = new ArrayList<>();
		when(repo.findByNameIgnoreCaseLikeOrderByIdAsc(SuchenUtils.suchenNach(ELTERNTEIL_NAME))).thenReturn(leereListe);

		// When
		List<Elternteil> gefundenListe = service.suchen(ELTERNTEIL_NAME);

		// Then
		assertThat(gefundenListe, is(emptyCollectionOf(Elternteil.class)));
	}

	// Then
	@Test(expected = EntityNichtGefundenException.class)
	public void keineElternteilGefundenMitDieseId() {
		// Given
		Long id = Long.MAX_VALUE;
		when(repo.findById(id)).thenReturn(Optional.empty());

		// When
		service.finden(id);

	}

	// Then
	@Test(expected = UsernameNotFoundException.class)
	public void keineElternteilMitDieseBenutzername() {
		// Given
		String benutzername = "ValidBenutzername11";
		when(repo.findByBenutzerBenutzername(benutzername)).thenReturn(Optional.empty());

		// When
		service.findeDurchBenutzername(benutzername);

	}

}
