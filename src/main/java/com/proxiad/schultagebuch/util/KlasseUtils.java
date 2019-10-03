package com.proxiad.schultagebuch.util;

import com.proxiad.schultagebuch.entity.Klasse;

public final class KlasseUtils {

	private KlasseUtils() {
		// nothing
	}

	public static Klasse getKlasseAusString(Klasse klasse, String klasseName) {
		klasse.setJahr(Integer.parseInt(klasseName.substring(0, klasseName.length() == 2 ? 1 : 2)));
		klasse.setBuchstabe(klasseName.substring(klasseName.length() - 1));
		return klasse;
	}

}
