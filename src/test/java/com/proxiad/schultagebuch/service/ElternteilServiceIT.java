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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.repository.ElternteilRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ElternteilServiceIT {

	private static final String ELTERNTEIL_NAME = "Stefan Stefanov";
	private static final String BENUTZERNAME = "ValidBenutzername11";
	private static final StringBuilder ELTERNTEIL_NAME_LIKE = new StringBuilder("%%").insert(1, ELTERNTEIL_NAME);

	@Mock
	private ElternteilRepository repo;

	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private ElternteilService service;

	@Test
	public void gefundenLeereListeDerEltern() {
		// Given
		when(repo.findByNameIgnoreCaseLikeOrderByIdAsc(ELTERNTEIL_NAME_LIKE.toString())).thenReturn(new ArrayList<>());

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
		service.findeDurchBenutzerName(benutzername, Locale.getDefault());
	}

	@Test(expected = IllegalArgumentException.class)
	public void keineElternteilGefundenMitDieseId() {
		// Given
		Long id = Long.MAX_VALUE;

		// When
		when(repo.findById(id)).thenReturn(Optional.empty());

		// Then
		service.elternteilFinde(id, Locale.getDefault());
	}

}
