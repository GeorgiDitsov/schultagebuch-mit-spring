package com.proxiad.schultagebuch.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.entity.Rolle;
import com.proxiad.schultagebuch.service.RolleService;

public final class PersonUtils {

	private PersonUtils() {
		// nothing
	}

	public static Object getPersonAusBenutzerName(final String benutzerName, final Object personService,
			final Locale locale) {
		Object person = null;
		try {
			person = personService.getClass().getMethod("findeNachBenutzerName", String.class, Locale.class)
					.invoke(personService, benutzerName, locale);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return person;
	}

	public static Object getNeuePerson(Object person, RolleService rolleServiceSupplier, final Locale locale) {
		try {
			person.getClass().getMethod("setBenutzer", Benutzer.class).invoke(person,
					getBenutzer(RolleUtils.getValidRolle(person, rolleServiceSupplier, locale)));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		return person;
	}

	private static Benutzer getBenutzer(Rolle validRolle) {
		Benutzer benutzer = new Benutzer();
		benutzer.setRolle(validRolle);
		return benutzer;
	}

}
