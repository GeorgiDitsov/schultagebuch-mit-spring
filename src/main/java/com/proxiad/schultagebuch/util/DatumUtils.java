package com.proxiad.schultagebuch.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DatumUtils {

	private static final String DATE_TIME_PATTERN = "dd-MMM-yyyy HH:mm:ss";

	private DatumUtils() {
		// nothing
	}

	public static String localDateTimeZuString(final LocalDateTime datum, final Locale locale) {
		return datum.format(dateTimeFormatter(locale));
	}

	public static LocalDateTime stringZuLocalDateTime(final String datumString, final Locale locale) {
		return LocalDateTime.parse(datumString, dateTimeFormatter(locale));
	}

	private static DateTimeFormatter dateTimeFormatter(final Locale locale) {
		return DateTimeFormatter.ofPattern(DATE_TIME_PATTERN, locale);
	}

}
