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
public class KlasseValidierungIT extends AbstraktEntityValidierungIT {

	@Autowired
	private Klasse klasse;

	@Before
	public void initTestContext() {
		klasse = new Klasse();
	}

	@Test
	public void richtigKlasseBuchstabe() {
		// Given
		klasse.setBuchstabe("a");

		// When
		Set<ConstraintViolation<Klasse>> violations = getValidator().validateProperty(klasse, "buchstabe");

		// Then
		assertThat(violations, is(empty()));
	}

	@Test
	public void falschKlasseBuchstabe() {
		// Given
		klasse.setBuchstabe("1");

		// When
		Set<ConstraintViolation<Klasse>> violations = getValidator().validateProperty(klasse, "buchstabe");

		// Then
		assertThat(violations, is(not(empty())));
	}
}
