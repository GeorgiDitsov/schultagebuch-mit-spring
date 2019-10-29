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

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.ElternteilRepository;
import com.proxiad.schultagebuch.util.SuchenUtils;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ElternteilServiceIT {

	private static final String ELTERNTEIL_NAME = "Stefan Stefanov";
	private static final String BENUTZERNAME = "ValidBenutzername11";

	@Mock
	private ElternteilRepository repo;

	@InjectMocks
	private ElternteilService service;

	@Test
	public void gefundenLeereListeDerEltern() {
		// Given
		when(repo.findByNameIgnoreCaseLikeOrderByIdAsc(SuchenUtils.suchenNach(ELTERNTEIL_NAME)))
				.thenReturn(new ArrayList<>());

		// When
		List<Elternteil> listOfEltern = service.suche(ELTERNTEIL_NAME);

		// Then
		assertThat(listOfEltern, is(emptyCollectionOf(Elternteil.class)));
	}

	@Test(expected = UsernameNotFoundException.class)
	public void keineElternteilMitDieseBenutzername() {
		// Given
		String benutzername = BENUTZERNAME;

		// When
		when(repo.findByBenutzerBenutzerName(benutzername)).thenReturn(Optional.empty());

		// Then
		service.findeDurchBenutzerName(benutzername);
	}

	@Test(expected = EntityNichtGefundenException.class)
	public void keineElternteilGefundenMitDieseId() {
		// Given
		Long id = Long.MAX_VALUE;

		// When
		when(repo.findById(id)).thenReturn(Optional.empty());

		// Then
		service.elternteilFinde(id);
	}

}
