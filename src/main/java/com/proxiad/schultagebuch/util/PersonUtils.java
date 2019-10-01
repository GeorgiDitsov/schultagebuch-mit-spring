package com.proxiad.schultagebuch.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.function.Supplier;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.entity.Rolle;
import com.proxiad.schultagebuch.service.RolleService;

public final class PersonUtils {

	private PersonUtils() {
		// nothing
	}

	public static Object getPersonNachBenutzername(final String benutzername, final Object personService,
			final Locale locale) {
		Object person = null;
		try {
			person = personService.getClass().getMethod("findByBenutzerName", String.class, Locale.class)
					.invoke(personService, benutzername, locale);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return person;
	}

	public static Object getNeuePerson(Object person, Supplier<RolleService> rolleServiceSupplier,
			final Locale locale) {
		try {
			person.getClass().getMethod("setBenutzer", Benutzer.class).invoke(person,
					getBenutzer(RolleUtils.getValidRole(person, rolleServiceSupplier, locale)));
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
