package com.proxiad.schultagebuch.localization;

import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

public class UrlLocaleInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		if (Objects.isNull(localeResolver)) {
			throw new IllegalStateException("Ohne LocaleResolver gefunden.");
		}
		Locale locale = localeResolver.resolveLocale(request);
		localeResolver.setLocale(request, response, locale);
		return true;
	}
}
