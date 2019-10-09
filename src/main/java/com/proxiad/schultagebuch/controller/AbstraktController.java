package com.proxiad.schultagebuch.controller;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public abstract class AbstraktController {

	protected ModelAndView ansicht(final String viewName) {
		ModelAndView view = new ModelAndView(viewName);
		return view;
	}

	protected ModelAndView ansicht(final String viewName, final String attributeName, final Object attributeValue) {
		ModelAndView view = new ModelAndView(viewName);
		view.addObject(attributeName, attributeValue);
		return view;
	}

	protected ModelAndView ansicht(final String viewName, final Map<String, Object> attributes) {
		ModelAndView view = new ModelAndView(viewName);
		attributes.forEach((attributeName, attributeValue) -> view.addObject(attributeName, attributeValue));
		return view;
	}

	protected RedirectView umleiten(final String url) {
		return new RedirectView(url);
	}

}
