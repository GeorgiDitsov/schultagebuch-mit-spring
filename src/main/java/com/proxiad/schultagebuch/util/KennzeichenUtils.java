package com.proxiad.schultagebuch.util;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.proxiad.schultagebuch.entity.Benutzer;

@Component
public class KennzeichenUtils {

	private KennzeichenUtils() {
		// nothing
	}

	public static String personKennzeichen(String personName, String personPin) {
		return personName + ", " + pinKennzeichen(personPin);
	}

	public static String pinKennzeichen(String personPin) {
		return personPin.substring(0, 6) + "****";
	}

	public static String benutzerNameKennzeichen(Benutzer benutzer) {
		return Optional.ofNullable(benutzer).isPresent() ? benutzer.getBenutzerName() : "n/a";
	}

}
