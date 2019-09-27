package com.proxiad.schultagebuch.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.service.ElternteilService;
import com.proxiad.schultagebuch.service.NoteService;
import com.proxiad.schultagebuch.service.SchulerService;

@Controller
@Validated
public class NoteController {

	@Autowired
	private NoteService noteService;

	@Autowired
	private SchulerService schulerService;

	@Autowired
	private ElternteilService elternteilService;

	@RequestMapping(value = "/my-grades")
	public ModelAndView schulerView(final Principal principal, final Locale locale) {
		return schulerNotenMav(new ModelAndView("notenForm"), getSchuler(principal, locale));
	}

	@RequestMapping(value = "/my-children")
	public ModelAndView elternteilView(final Principal principal, final Locale locale) {
		ModelAndView mav = new ModelAndView("notenForm");
		mav.addObject("elternteil", getEltenteil(principal, locale));
		return mav;
	}

	private ModelAndView schulerNotenMav(ModelAndView mav, Schuler schuler) {
		mav.addObject("schuler", schuler);
		mav.addObject("listNoten", noteService.findBySchuler(schuler));
		return mav;
	}

	private Schuler getSchuler(final Principal principal, final Locale locale) {
		return schulerService.findByBenutzerName(principal.getName(), locale);
	}

	private Elternteil getEltenteil(final Principal principal, final Locale locale) {
		return elternteilService.findByBenutzerName(principal.getName(), locale);
	}

}