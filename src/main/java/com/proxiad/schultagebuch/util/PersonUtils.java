package com.proxiad.schultagebuch.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.function.Supplier;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.service.RolleService;

public class PersonUtils {

	private static final RolleTyp FALSCH_ROLLE = null;

	private PersonUtils() {
		// nothing
	}

	public static Object getNeuePerson(Object person, Supplier<RolleService> rolleServiceSupplier,
			final Locale locale) {
		Benutzer benutzer = new Benutzer();
		benutzer.setRolle(
				rolleServiceSupplier.get().find(
						person instanceof Schuler ? RolleTyp.ROLLE_SCHULER
								: person instanceof Lehrer ? RolleTyp.ROLLE_LEHRER
										: person instanceof Elternteil ? RolleTyp.ROLLE_ELTERNTEIL : FALSCH_ROLLE,
						locale));
		try {
			person.getClass().getMethod("setBenutzer", Benutzer.class).invoke(person, benutzer);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		return person;
	}

}
