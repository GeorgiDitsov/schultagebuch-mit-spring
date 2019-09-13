package com.proxiad.schultagebuch.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.service.KlasseService;
import com.proxiad.schultagebuch.util.KlasseUtils;
import com.proxiad.schultagebuch.util.ValidierungsfehlerUtils;
import com.proxiad.schultagebuch.validator.constraint.KlasseNameConstraint;

@Controller
@Validated
public class KlasseController {

	@Autowired
	private KlasseService klasseService;

	@RequestMapping(value = "/klasse")
	public ModelAndView home() {
		return new ModelAndView("klasseForm", "listKlasse", klasseService.findAll());
	}

	@RequestMapping(value = "/klasse/add")
	public RedirectView emptyEntity(RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("klasse", new Klasse());
		return new RedirectView("/klasse");
	}

	@RequestMapping(value = "/klasse/edit/{id}")
	public RedirectView findForEdit(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		attributes.addFlashAttribute("add", false);
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("klasse", klasseService.find(id, locale));
		return new RedirectView("/klasse");
	}

	@PostMapping(value = "/klasse/add")
	public RedirectView save(RedirectAttributes attributes,
			@RequestParam(name = "klasseName") @KlasseNameConstraint String klasseName,
			@ModelAttribute(name = "klasse") Klasse klasse, final BindingResult bindingResult) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		klasseService.save(KlasseUtils.getInstance().bauenAusString(klasse, klasseName));
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/klasse");
	}

	@RequestMapping(value = "/klasse/delete/{id}")
	public RedirectView delete(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		klasseService.delete(klasseService.find(id, locale));
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/klasse");
	}

}
