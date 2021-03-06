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

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.service.ElternteilService;
import com.proxiad.schultagebuch.service.KlasseService;
import com.proxiad.schultagebuch.service.RolleService;
import com.proxiad.schultagebuch.service.SchulerService;
import com.proxiad.schultagebuch.util.BenutzerUtils;
import com.proxiad.schultagebuch.util.MenschUtils;

@Controller
@Validated
public class SchulerController extends AbstraktController {

	@Autowired
	private SchulerService schulerService;

	@Autowired
	private KlasseService klasseService;

	@Autowired
	private ElternteilService elternteilService;

	@Autowired
	private RolleService rolleService;

	@RequestMapping(value = "/schuler")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView alleSchulernAnzeigen() {
		return super.ansicht("schulerForm", "listSchuler", schulerService.findeAlle());
	}

	@RequestMapping(value = "/schuler/search")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView gefundenSchulernAnzeigen(@ModelAttribute(name = "string") final String schulerName) {
		return super.ansicht("schulerForm", "listSchuler", schulerService.suchen(schulerName));
	}

	@RequestMapping(value = "/schuler/add")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView neuerSchuler(@RequestHeader final String referer, final RedirectAttributes attributes) {
		Schuler neuerSchuler = MenschUtils.erstellenMenschMitRichtigeBenutzer(new Schuler(),
				BenutzerUtils.erstellenBenutzerMitRolle(rolleService.findenDurchMensch(Schuler.class)));
		modalAttributes("add", neuerSchuler, attributes);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/schuler/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehenderSchuler(@RequestHeader final String referer,
			@PathVariable(value = "id") final Long id, final RedirectAttributes attributes) {
		modalAttributes("edit", schulerService.finden(id), attributes);
		return super.umleiten(referer);
	}

	@PostMapping(value = "/schuler/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView schulerSpeichern(@RequestHeader final String referer,
			@ModelAttribute(name = "schuler") @Valid Schuler schuler, final BindingResult bindingResult,
			final RedirectAttributes attributes) {
		schulerService.speichern(schuler);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/schuler/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView schulerLoeschen(@RequestHeader final String referer, @PathVariable(value = "id") final Long id,
			final RedirectAttributes attributes) {
		schulerService.loeschen(id);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	private void modalAttributes(final String modalType, final Schuler schuler, final RedirectAttributes attributes) {
		attributes.addFlashAttribute(modalType, true);
		attributes.addFlashAttribute("schuler", schuler);
		attributes.addFlashAttribute("listKlasse", klasseService.findeAlle());
		attributes.addFlashAttribute("listEltern", elternteilService.findeAlle());
		attributes.addFlashAttribute("elternteil", MenschUtils.erstellenMenschMitRichtigeBenutzer(new Elternteil(),
				BenutzerUtils.erstellenBenutzerMitRolle(rolleService.findenDurchMensch(Elternteil.class))));
	}
}
