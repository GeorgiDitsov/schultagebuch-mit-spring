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

import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.service.KlasseService;
import com.proxiad.schultagebuch.service.LehrerService;
import com.proxiad.schultagebuch.service.SchulfachService;
import com.proxiad.schultagebuch.service.SchulstundeService;
import com.proxiad.schultagebuch.util.ValidierungsfehlerUtils;

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
	public ModelAndView alleSchulstundenZeigen() {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("listSchulstunde", schulstundeService.findAll());
		attributes.put("listKlasse", klasseService.findAll());
		attributes.put("listSchulfach", schulfachService.findAll());
		attributes.put("listLehrer", lehrerService.findAll());
		return super.ansicht("schulstundeForm", attributes);
	}

	@RequestMapping(value = "/schulstunde/add")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView neueSchulstunde(RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("schulstunde", new Schulstunde());
		return super.umleiten("/schulstunde");
	}

	@RequestMapping(value = "/schulstunde/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehendeSchulstunde(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", false);
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("schulstunde", schulstundeService.find(id, locale));
		return super.umleiten("/schulstunde");
	}

	@PostMapping(value = "/schulstunde/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView schulstundeSpeichern(@ModelAttribute(name = "schulstunde") @Valid Schulstunde schulstunde,
			final BindingResult bindingResult, RedirectAttributes attributes) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		schulstundeService.save(schulstunde);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/schulstunde");
	}

	@RequestMapping(value = "/schulstunde/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView schulstundeLoeschen(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		schulstundeService.delete(schulstundeService.find(id, locale));
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/schulstunde");
	}

}
