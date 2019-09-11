package com.proxiad.schultagebuch.localization;

import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.LocaleResolver;

public class UrlLocaleResolver implements LocaleResolver {

	private static final String URL_LOCATE_ATTRIBUTE_NAME = "URL_LOCATE_ATTRIBUTE_NAME";

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String suffixDe = request.getServletContext().getContextPath() + "/de";
		String suffixEn = request.getServletContext().getContextPath() + "/en";

		Locale locale = null;
		if (uri.endsWith(suffixEn)) {
			locale = Locale.UK;
		} else if (uri.endsWith(suffixDe)) {
			locale = Locale.GERMANY;
		}
		if (!Objects.isNull(locale)) {
			request.getSession().setAttribute(URL_LOCATE_ATTRIBUTE_NAME, locale);
		}
		if (Objects.isNull(locale)) {
			locale = (Locale) request.getSession().getAttribute(URL_LOCATE_ATTRIBUTE_NAME);
			if (Objects.isNull(locale)) {
				locale = Locale.GERMANY;
			}
		}

		return locale;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		// nothing
	}

}
