package com.proxiad.schultagebuch.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.service.NoteService;
import com.proxiad.schultagebuch.service.SchulerService;

@Controller
@Validated
public class NoteController {

	@Autowired
	private SchulerService schulerService;

	@Autowired
	private NoteService noteService;

	@RequestMapping(value = "/my-grades")
	public ModelAndView schulerView(final Principal principal, final Locale locale) {
		Schuler schuler = schulerService.findByBenutzerName(principal.getName(), locale);
		ModelAndView mav = new ModelAndView("notenForm");
		mav.addObject("schuler", schuler);
		mav.addObject("listNoten", noteService.findBySchulerAndSchulstunde(schuler));
		return mav;
	}
}
