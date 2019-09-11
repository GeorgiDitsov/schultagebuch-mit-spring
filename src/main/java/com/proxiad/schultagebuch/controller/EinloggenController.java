package com.proxiad.schultagebuch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class EinloggenController {

	@RequestMapping(value = "/login")
	public ModelAndView logIn() {
		return new ModelAndView("logInForm");
	}

	@RequestMapping(value = "/home")
	public ModelAndView home() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/{currentPage}/{locale:en|de}")
	public RedirectView locate(@PathVariable(value = "currentPage") String currentPage) {
		return new RedirectView("/" + currentPage);
	}

	@RequestMapping(value = "/info")
	public ModelAndView userInfo() {
		return new ModelAndView("info");
	}
}
