package com.proxiad.schultagebuch.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.exception.FalschServiceException;
import com.proxiad.schultagebuch.konstanten.StringKonstanten;

@Component
public final class KennzeichenUtils {

	private static final String GET_NAME = "getName";
	private static final String GET_PIN = "getPin";
	private static final String HIDDEN_NUMBERS = "****";

	private KennzeichenUtils() {
		// nothing
	}

	public static String menschKennzeichen(final Object mensch) {
		try {
			String name = (String) mensch.getClass().getMethod(GET_NAME).invoke(mensch);
			String pin = (String) mensch.getClass().getMethod(GET_PIN).invoke(mensch);
			return new StringBuilder().append(name).append(StringKonstanten.SEPARATOR).append(pinKennzeichen(pin))
					.toString();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new FalschServiceException();
		}
	}

	public static String pinKennzeichen(final String pin) {
		return new StringBuilder(pin.substring(0, 6)).append(HIDDEN_NUMBERS).toString();
	}

	public static String menschenKennzeichen(Collection<Object> menschen) {
		StringBuilder kennzeichen = new StringBuilder();
		Optional.of(menschen).filter(collection -> !collection.isEmpty())
				.ifPresent(collection -> collection.forEach(mensch -> kennzeichen
						.append(KennzeichenUtils.menschKennzeichen(mensch)).append(StringKonstanten.NEUE_ZEILE)));
		return menschen.isEmpty() ? StringKonstanten.OBJEKT_NICHT_VERFUEGBAR : kennzeichen.toString();
	}

	public static String benutzernameKennzeichen(final Benutzer benutzer) {
		return Optional.ofNullable(benutzer).map(Benutzer::getBenutzername)
				.orElse(StringKonstanten.OBJEKT_NICHT_VERFUEGBAR);
	}

}
