package com.proxiad.schultagebuch.util;

import java.util.Objects;

import com.proxiad.schultagebuch.entity.Klasse;

public class KlasseUtils implements EntityUtils<String, Klasse> {

	private static KlasseUtils singleInstance = null;

	private KlasseUtils() {
		// nothing
	}

	public static KlasseUtils getInstance() {
		if (Objects.isNull(singleInstance)) {
			singleInstance = new KlasseUtils();
		}
		return singleInstance;
	}

	@Override
	public Klasse bauden(String klasseName, Klasse klasse) {
		klasse.setJahr(Integer.parseInt(klasseName.substring(0, klasseName.length() == 2 ? 1 : 2)));
		klasse.setBuchstabe(klasseName.substring(klasseName.length() - 1));
		return klasse;
	}

}