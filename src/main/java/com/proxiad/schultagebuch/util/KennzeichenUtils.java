package com.proxiad.schultagebuch.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.konstanten.StringKonstanten;

@Component
public final class KennzeichenUtils {

	private static final String GET_NAME = "getName";
	private static final String GET_PIN = "getPin";
	private static final String HIDDEN_NUMBERS = "****";

	private KennzeichenUtils() {
		// nothing
	}

	public static String personKennzeichen(Object person) {
		String name = null;
		String pin = null;
		try {
			name = (String) person.getClass().getMethod(GET_NAME).invoke(person);
			pin = (String) person.getClass().getMethod(GET_PIN).invoke(person);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		return new StringBuilder(name).append(StringKonstanten.SEPARATOR).append(pinKennzeichen(pin)).toString();
	}

	public static String pinKennzeichen(String pin) {
		return new StringBuilder(pin.substring(0, 6)).append(HIDDEN_NUMBERS).toString();
	}

	public static String menschenKennzeichen(Collection<Object> menschen) {
		StringBuilder kennzeichen = new StringBuilder();
		Optional.of(menschen).filter(collection -> !collection.isEmpty())
				.ifPresent(collection -> collection.forEach(person -> kennzeichen
						.append(KennzeichenUtils.personKennzeichen(person)).append(StringKonstanten.NEUE_ZEILE)));
		return menschen.isEmpty() ? StringKonstanten.OBJEKT_NICHT_VERFUEGBAR : kennzeichen.toString();
	}

	public static String benutzerNameKennzeichen(Benutzer benutzer) {
		return Optional.ofNullable(benutzer).isPresent() ? benutzer.getBenutzerName()
				: StringKonstanten.OBJEKT_NICHT_VERFUEGBAR;
	}

}
