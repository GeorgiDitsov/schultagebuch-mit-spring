package com.proxiad.schultagebuch.util;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.entity.Rolle;

public final class BenutzerUtils {

	private BenutzerUtils() {
		// nothing
	}

	public static Benutzer erstellenBenutzerMitRolle(final Rolle rolle) {
		Benutzer benutzer = new Benutzer();
		benutzer.setRolle(rolle);
		return benutzer;
	}
}
