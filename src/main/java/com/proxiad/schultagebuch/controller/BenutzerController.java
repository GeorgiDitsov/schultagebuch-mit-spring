package com.proxiad.schultagebuch.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.service.BenutzerService;
import com.proxiad.schultagebuch.util.ValidierungsfehlerUtils;

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

	@RequestMapping(value = "/benutzer/edit/{id}")
	public RedirectView findEntity(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("benutzer", benutzerService.find(id, locale));
		return new RedirectView("/benutzer");
	}

	@PostMapping(value = "/benutzer/add")
	public RedirectView save(RedirectAttributes attributes, @ModelAttribute(name = "benutzer") @Valid Benutzer benutzer,
			final BindingResult bindingResult) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		benutzerService.save(benutzer);
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/benutzer");
	}

	@RequestMapping(value = "/benutzer/delete/{id}")
	public RedirectView delete(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		benutzerService.delete(benutzerService.find(id, locale));
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/benutzer");
	}

}
