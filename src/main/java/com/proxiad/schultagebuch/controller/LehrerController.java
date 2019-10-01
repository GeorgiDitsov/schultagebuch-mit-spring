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

import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.service.LehrerService;
import com.proxiad.schultagebuch.service.RolleService;
import com.proxiad.schultagebuch.service.SchulfachService;
import com.proxiad.schultagebuch.util.PersonUtils;
import com.proxiad.schultagebuch.util.ValidierungsfehlerUtils;

@Controller
@Validated
public class LehrerController {

	@Autowired
	private LehrerService lehrerService;

	@Autowired
	private SchulfachService schulfachService;

	@Autowired
	private RolleService rolleService;

	@RequestMapping(value = "/lehrer")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView showAllLehrer() {
		return allLehrerView(new ModelAndView("lehrerForm"));
	}

	@RequestMapping(value = "/lehrer/add")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView addLehrer(RedirectAttributes attributes, final Locale locale) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("lehrer", PersonUtils.getNeuePerson(new Lehrer(), () -> rolleService, locale));
		return new RedirectView("/lehrer");
	}

	@RequestMapping(value = "/lehrer/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView editLehrer(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		attributes.addFlashAttribute("add", false);
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("lehrer", lehrerService.find(id, locale));
		return new RedirectView("/lehrer");
	}

	@PostMapping(value = "/lehrer/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView saveLehrer(RedirectAttributes attributes, @ModelAttribute(name = "lehrer") @Valid Lehrer lehrer,
			final BindingResult bindingResult) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		lehrerService.save(lehrer);
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/lehrer");
	}

	@RequestMapping(value = "/lehrer/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView deleteLehrer(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		lehrerService.delete(lehrerService.find(id, locale));
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/lehrer");
	}

	private ModelAndView allLehrerView(ModelAndView view) {
		view.addObject("listLehrer", lehrerService.findAll());
		view.addObject("listSchulfach", schulfachService.findAll());
		return view;
	}

}
