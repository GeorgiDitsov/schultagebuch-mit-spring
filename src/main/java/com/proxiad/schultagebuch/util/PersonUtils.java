package com.proxiad.schultagebuch.util;

import java.lang.reflect.InvocationTargetException;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.exception.FalschServiceException;

public final class PersonUtils {

	private static final String FIND_BY_BENUTZERNAME_METHOD = "findeDurchBenutzername";
	private static final String SET_BENUTZER_METHOD = "setBenutzer";

	private PersonUtils() {
		// nothing
	}

	public static Object getPersonDurchBenutzername(final String benutzername, final Object personService) {
		Object person = new Object();
		try {
			person = personService.getClass().getMethod(FIND_BY_BENUTZERNAME_METHOD, String.class).invoke(personService,
					benutzername);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new FalschServiceException();
		}
		return person;
	}

	public static Object erstellenPersonMitValidBenutzer(final Object person, final Benutzer benutzer) {
		try {
			person.getClass().getMethod(SET_BENUTZER_METHOD, Benutzer.class).invoke(person, benutzer);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new FalschServiceException();
		}
		return person;
	}

}
