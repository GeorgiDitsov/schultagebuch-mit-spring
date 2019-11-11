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

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.service.BenutzerService;

@Controller
@Validated
public class BenutzerController extends AbstraktController {

	@Autowired
	private BenutzerService benutzerService;

	@RequestMapping(value = "/benutzer")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView alleBenutzerAnzeigen() {
		return super.ansicht("benutzerForm", "listBenutzer", benutzerService.findeAlle());
	}

	@RequestMapping(value = "/benutzer/search")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView gefundenBenutzerAnzeigen(@ModelAttribute(name = "string") final String benutzerName,
			RedirectAttributes attributes) {
		return super.ansicht("benutzerForm", "listBenutzer", benutzerService.suchen(benutzerName));
	}

	@RequestMapping(value = "/benutzer/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehendBenutzer(@RequestHeader final String referer,
			@PathVariable(value = "id") final Long id, RedirectAttributes attributes) {
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("benutzer", benutzerService.finden(id));
		return super.umleiten(referer);
	}

	@PostMapping(value = "/benutzer/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView benutzerSpeichern(@RequestHeader final String referer,
			@ModelAttribute(name = "benutzer") @Valid final Benutzer benutzer, final BindingResult bindingResult,
			RedirectAttributes attributes) {
		benutzerService.speichern(benutzer);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/benutzer/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView benutzerLoeschen(@RequestHeader final String referer, @PathVariable(value = "id") final Long id,
			RedirectAttributes attributes) {
		benutzerService.loeschen(id);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

}
