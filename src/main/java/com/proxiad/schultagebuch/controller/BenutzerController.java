package com.proxiad.schultagebuch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.service.BenutzerService;

@Controller
public class BenutzerController {

	@Autowired
	private BenutzerService benutzerService;

	@RequestMapping(value = "/benutzer")
	public ModelAndView home() {
		List<Benutzer> listBenutzer = benutzerService.findAll();
		ModelAndView mav = new ModelAndView("benutzerForm", "listBenutzer", listBenutzer);
		return mav;
	}
}
