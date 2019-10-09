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

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.service.ElternteilService;
import com.proxiad.schultagebuch.service.KlasseService;
import com.proxiad.schultagebuch.service.RolleService;
import com.proxiad.schultagebuch.service.SchulerService;
import com.proxiad.schultagebuch.util.PersonUtils;
import com.proxiad.schultagebuch.util.ValidierungUtils;

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
	public ModelAndView alleSchulernAnzeigen(final Locale locale) {
		return super.ansicht("schulerForm", "listSchuler", schulerService.findeAlle());
	}

	@RequestMapping(value = "/schuler/search")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView gefundenSchulernAnzeigen(@ModelAttribute(name = "string") final String schulerName) {
		return super.ansicht("schulerForm", "listSchuler", schulerService.suche(schulerName));
	}

	@RequestMapping(value = "/schuler/add")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView neuerSchuler(final Locale locale, RedirectAttributes attributes) {
		modalAttributes("add", 0, locale, attributes);
		return super.umleiten("/schuler");
	}

	@RequestMapping(value = "/schuler/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehenderSchuler(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		modalAttributes("edit", id, locale, attributes);
		return super.umleiten("/schuler");
	}

	@PostMapping(value = "/schuler/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView schulerSpeichern(@ModelAttribute(name = "schuler") @Valid Schuler schuler,
			final BindingResult bindingResult, RedirectAttributes attributes) {
		ValidierungUtils.fehlerPruefen(bindingResult);
		schulerService.speichern(schuler);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/schuler");
	}

	@RequestMapping(value = "/schuler/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView schulerLoeschen(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		schulerService.loeschen(schulerService.finden(id, locale));
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/schuler");
	}

	private void modalAttributes(final String modalType, final int schulerId, final Locale locale,
			RedirectAttributes attributes) {
		attributes.addFlashAttribute(modalType, true);
		attributes.addFlashAttribute("schuler",
				modalType.equals("add") ? PersonUtils.getNeuePerson(new Schuler(), rolleService, locale)
						: modalType.equals("edit") ? schulerService.finden(schulerId, locale) : null);
		attributes.addFlashAttribute("listKlasse", klasseService.findeAlle());
		attributes.addFlashAttribute("listEltern", elternteilService.findeAlle());
		attributes.addFlashAttribute("elternteil", PersonUtils.getNeuePerson(new Elternteil(), rolleService, locale));

	}
}
