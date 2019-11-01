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

import com.proxiad.schultagebuch.entity.Rolle;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.RolleRepository;
import com.proxiad.schultagebuch.util.RolleTyp;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RolleServiceIT {

	@Mock
	private RolleRepository repo;

	@InjectMocks
	private RolleService service;

	@Test
	public void leereListeGefunden() {
		// Given
		when(repo.findAllByOrderByIdAsc()).thenReturn(new ArrayList<>());

		// When
		List<Rolle> listOfRolle = service.findeAlle();

		// Then
		assertThat(listOfRolle, is(emptyCollectionOf(Rolle.class)));
	}

	@Test
	public void findeAlleTest() {
		// Given
		List<Rolle> listWithThreeRoles = new ArrayList<>();
		listWithThreeRoles.add(new Rolle());
		listWithThreeRoles.add(new Rolle());
		listWithThreeRoles.add(new Rolle());
		when(repo.findAllByOrderByIdAsc()).thenReturn(listWithThreeRoles);

		// When
		List<Rolle> foundList = service.findeAlle();

		// Then
		assertThat(foundList, hasSize(equalTo(listWithThreeRoles.size())));
	}

	@Test(expected = EntityNichtGefundenException.class)
	public void keineRolleGefundenDurchId() {
		// Given
		RolleTyp invalidName = null;

		// When
		when(repo.findByName(invalidName)).thenReturn(Optional.empty());

		// Then
		service.finden(invalidName);
	}

	@Test
	public void findenDurchNameTest() {
		// Given
		Rolle adminRolle = new Rolle(1, RolleTyp.ROLLE_ADMIN);
		when(repo.findByName(RolleTyp.ROLLE_ADMIN)).thenReturn(Optional.of(adminRolle));

		// When
		Rolle rolle = service.finden(RolleTyp.ROLLE_ADMIN);

		// Then
		assertThat(rolle, is(equalTo(adminRolle)));
	}

}
