package com.proxiad.schultagebuch.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.service.KlasseService;
import com.proxiad.schultagebuch.service.LehrerService;
import com.proxiad.schultagebuch.service.SchulfachService;
import com.proxiad.schultagebuch.service.SchulstundeService;
import com.proxiad.schultagebuch.util.ValidierungsfehlerUtils;

@Controller
@Validated
public class SchulstundeController {

	@Autowired
	private SchulstundeService schulstundeService;

	@Autowired
	private KlasseService klasseService;

	@Autowired
	private SchulfachService schulfachService;

	@Autowired
	private LehrerService lehrerService;

	@RequestMapping(value = "/schulstunde")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView showAllSchulstunden() {
		return allSchulstundenView(new ModelAndView("schulstundeForm"));
	}

	@RequestMapping(value = "/schulstunde/add")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView addSchulstunde(RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("schulstunde", new Schulstunde());
		return new RedirectView("/schulstunde");
	}

	@RequestMapping(value = "/schulstunde/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView editSchulstunde(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		attributes.addFlashAttribute("add", false);
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("schulstunde", schulstundeService.find(id, locale));
		return new RedirectView("/schulstunde");
	}

	@PostMapping(value = "/schulstunde/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView saveSchulstunde(RedirectAttributes attributes,
			@ModelAttribute(name = "schulstunde") @Valid Schulstunde schulstunde, final BindingResult bindingResult) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		schulstundeService.save(schulstunde);
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/schulstunde");
	}

	@RequestMapping(value = "/schulstunde/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView deleteSchulstunde(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		schulstundeService.delete(schulstundeService.find(id, locale));
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/schulstunde");
	}

	private ModelAndView allSchulstundenView(ModelAndView view) {
		view.addObject("listSchulstunde", schulstundeService.findAll());
		view.addObject("listKlasse", klasseService.findAll());
		view.addObject("listSchulfach", schulfachService.findAll());
		view.addObject("listLehrer", lehrerService.findAll());
		return view;
	}
}
