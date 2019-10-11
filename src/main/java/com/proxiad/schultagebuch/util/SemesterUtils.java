package com.proxiad.schultagebuch.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.proxiad.schultagebuch.entity.Semester;

public final class SemesterUtils {

	private static final String DATE_FORMATTER_PATTERN = "yyyy-MM-dd";

	private SemesterUtils() {
		// nothing
	}

	public static Semester setSemesterDatum(Semester semester, String begin, String end) {
		semester.setSemesterbeginn(stringToLocalDateTime(begin));
		semester.setSemesterende(stringToLocalDateTime(end));
		return semester;
	}

	private static LocalDateTime stringToLocalDateTime(String date) {
		return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMATTER_PATTERN)).atStartOfDay();
	}
}
