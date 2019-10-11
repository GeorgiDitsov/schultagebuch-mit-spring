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
import com.proxiad.schultagebuch.util.ValidierungUtils;

@Controller
@Validated
public class LehrerController extends AbstraktController {

	@Autowired
	private LehrerService lehrerService;

	@Autowired
	private SchulfachService schulfachService;

	@Autowired
	private RolleService rolleService;

	@RequestMapping(value = "/lehrer")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView alleLehrerAnzeigen() {
		return super.ansicht("lehrerForm", "listLehrer", lehrerService.findeAlle());
	}

	@RequestMapping(value = "/lehrer/search")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView gefundenLehrernAnzeigen(@ModelAttribute(name = "string") final String lehrerName) {
		return super.ansicht("lehrerForm", "listLehrer", lehrerService.suche(lehrerName));
	}

	@RequestMapping(value = "/lehrer/add")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView neuerLehrer(final Locale locale, RedirectAttributes attributes) {
		modalAttributes("add", (Lehrer) PersonUtils.getNeuePerson(new Lehrer(), rolleService, locale), attributes);
		return super.umleiten("/lehrer");
	}

	@RequestMapping(value = "/lehrer/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehenderLehrer(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		modalAttributes("edit", lehrerService.finden(id, locale), attributes);
		return super.umleiten("/lehrer");
	}

	@PostMapping(value = "/lehrer/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView lehrerSpeichern(@ModelAttribute(name = "lehrer") @Valid Lehrer lehrer,
			final BindingResult bindingResult, RedirectAttributes attributes) {
		ValidierungUtils.fehlerPruefen(bindingResult);
		lehrerService.speichern(lehrer);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/lehrer");
	}

	@RequestMapping(value = "/lehrer/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView lehrerLoeschen(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		lehrerService.loeschen(lehrerService.finden(id, locale));
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/lehrer");
	}

	private void modalAttributes(final String modalType, final Lehrer lehrer, RedirectAttributes attributes) {
		attributes.addFlashAttribute(modalType, true);
		attributes.addFlashAttribute("listSchulfach", schulfachService.findeAlle());
		attributes.addFlashAttribute("lehrer", lehrer);
	}

}
