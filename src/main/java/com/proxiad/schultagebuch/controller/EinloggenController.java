package com.proxiad.schultagebuch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping(value = "/info")
	public ModelAndView userInfo() {
		return new ModelAndView("info");
	}
}
