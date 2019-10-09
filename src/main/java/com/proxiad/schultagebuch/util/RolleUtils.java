package com.proxiad.schultagebuch.util;

import java.util.Locale;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.entity.Rolle;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.service.RolleService;

public final class RolleUtils {

	private static final RolleTyp FALSCH_ROLLE = null;

	private RolleUtils() {
		// nothing
	}

	public static Rolle getValidRolle(final Object person, RolleService rolleServiceSupplier, final Locale locale) {
		return rolleServiceSupplier.finden(person instanceof Schuler ? RolleTyp.ROLLE_SCHULER
				: person instanceof Lehrer ? RolleTyp.ROLLE_LEHRER
						: person instanceof Elternteil ? RolleTyp.ROLLE_ELTERNTEIL : FALSCH_ROLLE,
				locale);
	}
}
