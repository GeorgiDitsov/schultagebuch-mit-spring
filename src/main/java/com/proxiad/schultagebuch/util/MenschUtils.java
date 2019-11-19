package com.proxiad.schultagebuch.util;

import java.lang.reflect.InvocationTargetException;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.exception.FalschServiceException;

public final class MenschUtils {

	private static final String FIND_BY_BENUTZERNAME_METHOD = "findeDurchBenutzername";
	private static final String SET_BENUTZER_METHOD = "setBenutzer";

	private MenschUtils() {
		// nothing
	}

	public static Object getMenschDurchBenutzername(final String benutzername, final Object menschService) {
		try {
			return menschService.getClass().getMethod(FIND_BY_BENUTZERNAME_METHOD, String.class).invoke(menschService,
					benutzername);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new FalschServiceException();
		}
	}

	public static <T> T erstellenMenschMitRichtigeBenutzer(final T mensch, final Benutzer benutzer) {
		try {
			mensch.getClass().getMethod(SET_BENUTZER_METHOD, Benutzer.class).invoke(mensch, benutzer);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new FalschServiceException();
		}
		return mensch;
	}

}
