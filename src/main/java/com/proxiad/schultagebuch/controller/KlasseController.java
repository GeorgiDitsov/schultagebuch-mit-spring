package com.proxiad.schultagebuch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
	public RedirectView neueKlasse(@RequestHeader final String referer, RedirectAttributes attributes) {
		modalAttributes("add", new Klasse(), attributes);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/klasse/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehendeKlasse(@RequestHeader final String referer, @PathVariable(value = "id") final Long id,
			RedirectAttributes attributes) {
		modalAttributes("edit", klasseService.finden(id), attributes);
		return super.umleiten(referer);
	}

	@PostMapping(value = "/klasse/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView klasseSpeichern(@RequestHeader final String referer,
			@RequestParam(name = "klasseName") @KlasseNameConstraint final String klasseName,
			@ModelAttribute(name = "klasse") Klasse klasse, final BindingResult bindingResult,
			RedirectAttributes attributes) {
		ValidierungUtils.fehlerPruefen(bindingResult);
		klasseService.speichern(KlasseUtils.setKlasseAusString(klasse, klasseName));
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/klasse/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView klasseLoeschen(@RequestHeader final String referer, @PathVariable(value = "id") final Long id,
			RedirectAttributes attributes) {
		klasseService.loeschen(id);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	private void modalAttributes(final String modalType, final Klasse klasse, RedirectAttributes attributes) {
		attributes.addFlashAttribute(modalType, true);
		attributes.addFlashAttribute("klasse", klasse);
	}

}
