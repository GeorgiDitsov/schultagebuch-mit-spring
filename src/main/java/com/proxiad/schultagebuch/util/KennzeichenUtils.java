package com.proxiad.schultagebuch.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.proxiad.schultagebuch.entity.Benutzer;

@Component
public class KennzeichenUtils {

	private KennzeichenUtils() {
		// nothing
	}

	public static String personKennzeichen(Object person) {
		String name = null;
		String pin = null;
		try {
			name = (String) person.getClass().getMethod("getName").invoke(person);
			pin = (String) person.getClass().getMethod("getPin").invoke(person);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		return name + ", " + pinKennzeichen(pin);
	}

	public static String pinKennzeichen(String pin) {
		return pin.substring(0, 6) + "****";
	}

	public String menschenKennzeichen(Collection<Object> menschen) {
		StringBuilder kennzeichen = new StringBuilder();
		Optional.of(menschen).filter(collection -> !collection.isEmpty()).ifPresent(collection -> collection
				.forEach(person -> kennzeichen.append(KennzeichenUtils.personKennzeichen(person)).append("\n")));
		return menschen.isEmpty() ? "n/a" : kennzeichen.toString();
	}

	public static String benutzerNameKennzeichen(Benutzer benutzer) {
		return Optional.ofNullable(benutzer).isPresent() ? benutzer.getBenutzerName() : "n/a";
	}

}
