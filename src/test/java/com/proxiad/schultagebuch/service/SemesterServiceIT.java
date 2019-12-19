package com.proxiad.schultagebuch.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.repository.SemesterRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SemesterServiceIT {

	@Mock
	private SemesterRepository repo;

	@InjectMocks
	private SemesterService service;

	@Test
	public void gefundenLeereListeDerSemester() {
		// Given
		List<Semester> leereListe = new ArrayList<>();
		when(repo.findAllByOrderByIdAsc()).thenReturn(leereListe);

		// When
		List<Semester> listeGefunden = service.findeAlle();

		// Then
		assertThat(listeGefunden, is(emptyCollectionOf(Semester.class)));
	}

	@Test
	public void findeAlleTest() {
		// Given
		List<Semester> listeDerSemester = new ArrayList<>();
		listeDerSemester.add(new Semester(Integer.MAX_VALUE, LocalDateTime.of(2019, 3, 3, 0, 0),
				LocalDateTime.of(2019, 4, 4, 0, 0)));
		listeDerSemester.add(new Semester(Integer.MAX_VALUE, LocalDateTime.of(2019, 1, 1, 0, 0),
				LocalDateTime.of(2019, 2, 2, 0, 0)));
		when(repo.findAllByOrderByIdAsc()).thenReturn(listeDerSemester);

		// When
		List<Semester> listeGefunden = service.findeAlle();

		// Then
		assertThat(listeGefunden, hasSize(equalTo(listeDerSemester.size())));
	}

}
