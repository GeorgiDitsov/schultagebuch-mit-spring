package com.proxiad.schultagebuch.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.proxiad.schultagebuch.util.ValidierungUtils;
import com.proxiad.schultagebuch.validator.constraint.KlasseNameConstraint;

@Controller
@Validated
public class KlasseController extends AbstraktController {

	@Autowired
	private KlasseService klasseService;

	@RequestMapping(value = "/klasse")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView alleKlassenAnzeigen() {
		return super.ansicht("klasseForm", "listKlasse", klasseService.findeAlle());
	}

	@RequestMapping(value = "/klasse/add")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView neueKlasse(RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("klasse", new Klasse());
		return super.umleiten("/klasse");
	}

	@RequestMapping(value = "/klasse/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehendeKlasse(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("klasse", klasseService.finden(id, locale));
		return super.umleiten("/klasse");
	}

	@PostMapping(value = "/klasse/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView klasseSpeichern(@RequestParam(name = "klasseName") @KlasseNameConstraint String klasseName,
			@ModelAttribute(name = "klasse") Klasse klasse, final BindingResult bindingResult,
			RedirectAttributes attributes) {
		ValidierungUtils.fehlerPruefen(bindingResult);
		klasseService.speichern(KlasseUtils.getKlasseAusString(klasse, klasseName));
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/klasse");
	}

	@RequestMapping(value = "/klasse/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView klasseLoeschen(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		klasseService.loeschen(klasseService.finden(id, locale));
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/klasse");
	}

}
