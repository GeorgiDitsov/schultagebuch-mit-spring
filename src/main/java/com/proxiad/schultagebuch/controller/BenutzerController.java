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

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.service.BenutzerService;
import com.proxiad.schultagebuch.util.ValidierungsfehlerUtils;

@Controller
@Validated
public class BenutzerController extends AbstraktController {

	@Autowired
	private BenutzerService benutzerService;

	@RequestMapping(value = "/benutzer")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView alleBenutzerZeigen() {
		return super.ansicht("benutzerForm", "listBenutzer", benutzerService.findAll());
	}

	@RequestMapping(value = "/benutzer/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehendBenutzer(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("benutzer", benutzerService.find(id, locale));
		return super.umleiten("/benutzer");
	}

	@PostMapping(value = "/benutzer/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView benutzerSpeichern(@ModelAttribute(name = "benutzer") @Valid final Benutzer benutzer,
			final BindingResult bindingResult, RedirectAttributes attributes) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		benutzerService.save(benutzer);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/benutzer");
	}

	@RequestMapping(value = "/benutzer/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView benutzerLoeschen(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		benutzerService.delete(benutzerService.find(id, locale));
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/benutzer");
	}

}
