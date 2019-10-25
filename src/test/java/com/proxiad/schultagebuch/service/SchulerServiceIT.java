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

import com.proxiad.schultagebuch.entity.Schuler;
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
	public void gefindenLeereListeDerSchuler() {
		// Given
		when(repo.findAllByOrderByIdAsc()).thenReturn(new ArrayList<>());

		// When
		List<Schuler> listOfSchuler = service.findeAlle();

		// Then
		assertThat(listOfSchuler, is(emptyCollectionOf(Schuler.class)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void keineSchulerGefinden() {
		// Given
		Long id = Long.MAX_VALUE;

		// When
		when(repo.findById(id)).thenReturn(Optional.empty());

		// Then
		service.finden(id, Locale.getDefault());
	}

}
