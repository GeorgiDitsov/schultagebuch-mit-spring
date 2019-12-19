package com.proxiad.schultagebuch.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SchulfachValidierungIT extends AbstraktEntityValidierungIT {

	@Autowired
	private Schulfach schulfach;

	@Before
	public void initTestContext() {
		schulfach = new Schulfach();
	}

	@Test
	public void richtigSchulfachName() {
		// Given
		schulfach.setName("Schulfach");

		// When
		Set<ConstraintViolation<Schulfach>> violations = getValidator().validate(schulfach);

		// Then
		assertThat(violations, is(empty()));
	}

	@Test
	public void falschSchulfachName() {
		// Given
		schulfach.setName("SchulFach");

		// When
		Set<ConstraintViolation<Schulfach>> violations = getValidator().validate(schulfach);

		// Then
		assertThat(violations, is(not(empty())));
	}

}
