package com.proxiad.schultagebuch.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulstunde;

public class BerechnungUtilsIT {

	@Test
	public void durchschnittlichNotenAusLeereCollectionOfNoten() {
		// Given
		Collection<Note> notenCollection = new ArrayList<>();

		// When
		Long durchschnittlichNote = BerechnungUtils.durchschnittlichNoten(notenCollection);

		// Then
		assertThat(durchschnittlichNote, is(equalTo(0L)));
	}

	@Test
	public void durchschnittlichNotenTest() {
		// Given
		Collection<Note> notenCollection = new ArrayList<>();
		Schuler schuler = new Schuler();
		Schulstunde schulstunde = new Schulstunde();
		notenCollection.add(
				new Note(Long.MAX_VALUE, (byte) 5, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));
		notenCollection.add(
				new Note(Long.MAX_VALUE, (byte) 4, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));
		notenCollection.add(
				new Note(Long.MAX_VALUE, (byte) 3, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));
		notenCollection.add(
				new Note(Long.MAX_VALUE, (byte) 2, LocalDateTime.now(), LocalDateTime.now(), schuler, schulstunde));

		// When
		Long durchschnittlichNote = BerechnungUtils.durchschnittlichNoten(notenCollection);

		// Then
		assertThat(durchschnittlichNote, is(equalTo(4L)));
	}

	@Test
	public void durchschnittlichHalbjaehrigeNotenTest() {
		// Given
		Collection<Long> halbjaehrigeNotenCollection = new ArrayList<>();
		halbjaehrigeNotenCollection.add(2L);
		halbjaehrigeNotenCollection.add(3L);
		halbjaehrigeNotenCollection.add(4L);
		halbjaehrigeNotenCollection.add(5L);

		// When
		double result = BerechnungUtils.durchschnittlichHalbjaehrigeNoten(halbjaehrigeNotenCollection);

		// Then
		assertThat(result, is(equalTo(3.5)));
	}

	@Test
	public void durchschnittlichHalbjaehrigeNotenFalschNoten() {
		// Given
		Collection<Long> halbjaehrigeNotenCollection = new ArrayList<>();
		halbjaehrigeNotenCollection.add(1L);
		halbjaehrigeNotenCollection.add(0L);
		halbjaehrigeNotenCollection.add(7L);
		halbjaehrigeNotenCollection.add(21L);

		// When
		double result = BerechnungUtils.durchschnittlichHalbjaehrigeNoten(halbjaehrigeNotenCollection);

		// Then
		assertThat(result, is(equalTo(Double.NaN)));

	}

	@Test
	public void durchschnittlichHalbjaehrigeNotenAusLeereCollectionOfHalbjaehrigeNoten() {
		// Given
		Collection<Long> halbjaehrigeNotenCollection = new ArrayList<>();

		// When
		double result = BerechnungUtils.durchschnittlichHalbjaehrigeNoten(halbjaehrigeNotenCollection);

		// Then
		assertThat(result, is(equalTo(Double.NaN)));
	}

}
