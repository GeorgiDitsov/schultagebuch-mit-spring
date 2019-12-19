package com.proxiad.schultagebuch.localization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LocalizationConfig implements WebMvcConfigurer {

	private static final String GERMAN_PATTERN = "/de";
	private static final String ENGLISH_PATTERN = "/en";

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		UrlLocaleInterceptor localeInterceptor = new UrlLocaleInterceptor();
		registry.addInterceptor(localeInterceptor).addPathPatterns(GERMAN_PATTERN, ENGLISH_PATTERN);
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new UrlLocaleResolver();
	}

}
