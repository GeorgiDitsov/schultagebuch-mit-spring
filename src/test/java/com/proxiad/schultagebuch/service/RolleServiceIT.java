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

import com.proxiad.schultagebuch.entity.Lehrer;
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
	public void gefundenLeereListeDerRollen() {
		// Given
		List<Rolle> leereListe = new ArrayList<>();

		// When
		when(repo.findAllByOrderByIdAsc()).thenReturn(leereListe);
		List<Rolle> gefundenListe = service.findeAlle();

		// Then
		assertThat(gefundenListe, is(emptyCollectionOf(Rolle.class)));
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
	public void keineRolleGefundenDurchPerson() {
		// Given
		RolleTyp falschName = null;

		// When
		when(repo.findByName(falschName)).thenReturn(Optional.empty());

		// Then
		service.findenDurchMensch(Object.class);
	}

	@Test
	public void findeDurchPersonTest() {
		// Given
		Rolle lehrerRolle = new Rolle(1, RolleTyp.ROLLE_LEHRER);
		when(repo.findByName(RolleTyp.ROLLE_LEHRER)).thenReturn(Optional.of(lehrerRolle));

		// When
		Rolle rolle = service.findenDurchMensch(Lehrer.class);

		// Then
		assertThat(rolle, is(equalTo(lehrerRolle)));
	}

}
