package com.proxiad.schultagebuch.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Validated
public class BaseController {

	@RequestMapping(value = "/login")
	@PreAuthorize("isAnonymous()")
	public ModelAndView logIn() {
		return new ModelAndView("anmeldungForm");
	}

	@RequestMapping(value = "/home")
	@PreAuthorize("!isAnonymous()")
	public ModelAndView home() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/*/{locale:en|de}")
	@PreAuthorize("!isAnonymous()")
	public RedirectView locate(@RequestHeader String referer) {
		return new RedirectView(referer);
	}

	@RequestMapping(value = "/info")
	@PreAuthorize("!isAnonymous()")
	public ModelAndView userInfo() {
		return new ModelAndView("info");
	}

	@RequestMapping(value = "/unauthorized")
	public ModelAndView unauthorized() {
		return new ModelAndView("error/401");
	}

}
