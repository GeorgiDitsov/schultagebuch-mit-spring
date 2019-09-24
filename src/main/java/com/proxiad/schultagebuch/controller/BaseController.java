package com.proxiad.schultagebuch.controller;

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
	public ModelAndView logIn() {
		return new ModelAndView("logInForm");
	}

	@RequestMapping(value = "/home")
	public ModelAndView home() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/*/{locale:en|de}")
	public RedirectView locate(@RequestHeader String referer) {
		return new RedirectView(referer);
	}

	@RequestMapping(value = "/info")
	public ModelAndView userInfo() {
		return new ModelAndView("info");
	}

}
