package com.proxiad.schultagebuch.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
import com.proxiad.schultagebuch.util.ValidierungsfehlerUtils;

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
	public ModelAndView alleSchulernZeigen(final Locale locale) {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("listSchuler", schulerService.findAll());
		attributes.put("listKlasse", klasseService.findAll());
		attributes.put("listEltern", elternteilService.findAll());
		attributes.put("elternteil", PersonUtils.getNeuePerson(new Elternteil(), rolleService, locale));
		return super.ansicht("schulerForm", attributes);
	}

	@RequestMapping(value = "/schuler/add")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView neuerSchuler(final Locale locale, RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("schuler", PersonUtils.getNeuePerson(new Schuler(), rolleService, locale));
		return super.umleiten("/schuler");
	}

	@RequestMapping(value = "/schuler/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehenderSchuler(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", false);
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("schuler", schulerService.find(id, locale));
		return super.umleiten("/schuler");
	}

	@PostMapping(value = "/schuler/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView schulerSpeichern(@ModelAttribute(name = "schuler") @Valid Schuler schuler,
			final BindingResult bindingResult, RedirectAttributes attributes) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		schulerService.save(schuler);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/schuler");
	}

	@RequestMapping(value = "/schuler/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView schulerLoeschen(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		schulerService.delete(schulerService.find(id, locale));
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/schuler");
	}

}
