package com.proxiad.schultagebuch.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SemesterValidierungIT extends AbstraktEntityValidierungIT {

	@Autowired
	private Semester semester;

	@Before
	public void initTestContext() {
		semester = new Semester();
	}

	@Test
	public void semesterbeginnVorSemesterende() {
		// Given
		semester.setSemesterbeginn(LocalDateTime.MIN);
		semester.setSemesterende(LocalDateTime.MAX);

		// When
		Set<ConstraintViolation<Semester>> violations = getValidator().validate(semester);

		// Then
		assertThat(violations, is(empty()));
	}

	@Test
	public void semesterbeginnNachSemesterEnde() {
		// Given
		semester.setSemesterbeginn(LocalDateTime.MAX);
		semester.setSemesterende(LocalDateTime.MIN);

		// When
		Set<ConstraintViolation<Semester>> violations = getValidator().validate(semester);

		// Then
		assertThat(violations, is(not(empty())));
	}

}
