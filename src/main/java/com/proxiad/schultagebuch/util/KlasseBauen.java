package com.proxiad.schultagebuch.util;

import java.util.Objects;

import com.proxiad.schultagebuch.entity.Klasse;

public class KlasseBauen implements EntityBauen<String, Klasse> {

	private static KlasseBauen singleInstance = null;

	private KlasseBauen() {
		// nothing
	}

	public static KlasseBauen getInstance() {
		if (Objects.isNull(singleInstance)) {
			singleInstance = new KlasseBauen();
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
