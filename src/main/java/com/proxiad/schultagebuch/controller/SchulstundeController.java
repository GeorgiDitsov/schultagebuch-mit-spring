package com.proxiad.schultagebuch.controller;

import javax.validation.Valid;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.service.KlasseService;
import com.proxiad.schultagebuch.service.LehrerService;
import com.proxiad.schultagebuch.service.SchulfachService;
import com.proxiad.schultagebuch.service.SchulstundeService;

@Controller
@Validated
public class SchulstundeController extends AbstraktController {

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
	public ModelAndView alleSchulstundenAnzeigen() {
		return super.ansicht("schulstundeForm", "listSchulstunde", schulstundeService.findeAlle());
	}

	@RequestMapping(value = "/schulstunde/add")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView neueSchulstunde(@RequestHeader final String referer, final RedirectAttributes attributes) {
		modalAttributes("add", new Schulstunde(), attributes);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/schulstunde/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehendeSchulstunde(@RequestHeader final String referer,
			@PathVariable(value = "id") final Long id, final RedirectAttributes attributes) {
		modalAttributes("edit", schulstundeService.finden(id), attributes);
		return super.umleiten(referer);
	}

	@PostMapping(value = "/schulstunde/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView schulstundeSpeichern(@RequestHeader final String referer,
			@ModelAttribute(name = "schulstunde") @Valid Schulstunde schulstunde, final BindingResult bindingResult,
			final RedirectAttributes attributes) {
		schulstundeService.speichern(schulstunde);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/schulstunde/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView schulstundeLoeschen(@RequestHeader final String referer,
			@PathVariable(value = "id") final Long id, final RedirectAttributes attributes) {
		schulstundeService.loeschen(id);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	private void modalAttributes(final String modalType, final Schulstunde schulstunde,
			final RedirectAttributes attributes) {
		attributes.addFlashAttribute(modalType, true);
		attributes.addFlashAttribute("schulstunde", schulstunde);
		attributes.addFlashAttribute("listKlasse", klasseService.findeAlle());
		attributes.addFlashAttribute("listSchulfach", schulfachService.findeAlle());
		attributes.addFlashAttribute("listLehrer", lehrerService.findeAlle());
	}

}
