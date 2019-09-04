package com.proxiad.schultagebuch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.service.LehrerService;

@Controller
public class LehrerController {

	@Autowired
	private LehrerService lehrerService;

	@RequestMapping(value = "/lehrer")
	public ModelAndView home(Authentication authentication) {
		List<Lehrer> listLehrer = lehrerService.findAll();
		ModelAndView mav = new ModelAndView("lehrerForm", "listLehrer", listLehrer);
		mav.addObject("authentication", authentication);
		return mav;
	}
}
