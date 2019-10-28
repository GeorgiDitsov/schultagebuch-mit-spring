package com.proxiad.schultagebuch.util;

public final class SuchenUtils {

	private static final String PERCENTS = "%%";

	private SuchenUtils() {
		// nothing
	}

	public static String suchenNach(String word) {
		return new StringBuilder(PERCENTS).insert(1, word).toString();
	}

}
