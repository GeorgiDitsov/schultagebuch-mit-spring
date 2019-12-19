package com.proxiad.schultagebuch.util;

import java.util.Collection;

import com.proxiad.schultagebuch.entity.Note;

public final class BerechnungUtils {

	private BerechnungUtils() {
		// nothing
	}

	public static Long durchschnittlichNoten(final Collection<Note> noten) {
		return Math.round(noten.stream().mapToDouble(Note::getWert).average().orElse(Double.NaN));
	}

	public static double durchschnittlichHalbjaehrigeNoten(final Collection<Long> collectionOfHalbjaehrigeNoten) {
		return collectionOfHalbjaehrigeNoten.stream().filter(noteWert -> noteWert >= 2 && noteWert <= 6)
				.mapToDouble(Long::doubleValue).average().orElse(Double.NaN);
	}

}
