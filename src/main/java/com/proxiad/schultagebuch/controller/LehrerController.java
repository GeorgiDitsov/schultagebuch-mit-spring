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
import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.service.LehrerService;
import com.proxiad.schultagebuch.service.RolleService;
import com.proxiad.schultagebuch.service.SchulfachService;
import com.proxiad.schultagebuch.util.BenutzerUtils;
import com.proxiad.schultagebuch.util.MenschUtils;

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
	public RedirectView neuerLehrer(@RequestHeader final String referer, final RedirectAttributes attributes) {
		Benutzer benutzer = BenutzerUtils.erstellenBenutzerMitRolle(rolleService.findenDurchMensch(Lehrer.class));
		Lehrer neuerLehrer = MenschUtils.erstellenMenschMitRichtigeBenutzer(new Lehrer(), benutzer);
		modalAttributes("add", neuerLehrer, attributes);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/lehrer/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehenderLehrer(@RequestHeader final String referer,
			@PathVariable(value = "id") final Long id, final RedirectAttributes attributes) {
		modalAttributes("edit", lehrerService.finden(id), attributes);
		return super.umleiten(referer);
	}

	@PostMapping(value = "/lehrer/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView lehrerSpeichern(@RequestHeader final String referer,
			@ModelAttribute(name = "lehrer") @Valid Lehrer lehrer, final BindingResult bindingResult,
			final RedirectAttributes attributes) {
		lehrerService.speichern(lehrer);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/lehrer/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView lehrerLoeschen(@RequestHeader final String referer, @PathVariable(value = "id") final Long id,
			final RedirectAttributes attributes) {
		lehrerService.loeschen(id);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	private void modalAttributes(final String modalType, final Lehrer lehrer, final RedirectAttributes attributes) {
		attributes.addFlashAttribute(modalType, true);
		attributes.addFlashAttribute("listSchulfach", schulfachService.findeAlle());
		attributes.addFlashAttribute("lehrer", lehrer);
	}

}
