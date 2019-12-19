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
public class BaseController extends AbstraktController {

	private static final String LOG_IN_VIEW = "anmeldungForm";
	private static final String HOME_VIEW = "index";
	private static final String INFO_VIEW = "info";
	private static final String UNAUTHORIZED_VIEW = "error/401";

	@RequestMapping(value = "/login")
	@PreAuthorize("isAnonymous()")
	public ModelAndView anmeldung() {
		return super.ansicht(LOG_IN_VIEW);
	}

	@RequestMapping(value = "/home")
	@PreAuthorize("!isAnonymous()")
	public ModelAndView home() {
		return super.ansicht(HOME_VIEW);
	}

	@RequestMapping(value = { "/*/{locale:en|de}", "/*/*/{locale:en|de}", "/*/*/*/{locale:en|de}",
			"/*/*/*/*/*/{locale:en|de}" })
	@PreAuthorize("!isAnonymous()")
	public RedirectView lokalisieren(@RequestHeader final String referer) {
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/info")
	@PreAuthorize("!isAnonymous()")
	public ModelAndView info() {
		return super.ansicht(INFO_VIEW);
	}

	@RequestMapping(value = "/unauthorized")
	public ModelAndView unauthorized() {
		return super.ansicht(UNAUTHORIZED_VIEW);
	}

}
