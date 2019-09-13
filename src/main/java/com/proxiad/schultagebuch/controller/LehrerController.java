package com.proxiad.schultagebuch.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.service.LehrerService;
import com.proxiad.schultagebuch.service.RolleService;
import com.proxiad.schultagebuch.service.SchulfachService;
import com.proxiad.schultagebuch.util.ValidierungsfehlerUtils;

@Controller
public class LehrerController {

	@Autowired
	private LehrerService lehrerService;

	@Autowired
	private SchulfachService schulfachService;

	@Autowired
	private RolleService rolleService;

	@RequestMapping(value = "/lehrer")
	public ModelAndView home() {
		return homeMav(new ModelAndView("lehrerForm"));
	}

	@RequestMapping(value = "/lehrer/add")
	public RedirectView newEntity(RedirectAttributes attributes, final Locale locale) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("lehrer", setBenutzerRolle(new Lehrer(), locale));
		return new RedirectView("/lehrer");
	}

	@PostMapping(value = "/lehrer/add")
	public RedirectView save(RedirectAttributes attributes, @ModelAttribute(name = "lehrer") @Valid Lehrer lehrer,
			final BindingResult bindingResult) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		lehrerService.save(lehrer);
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/lehrer");
	}

	@RequestMapping(value = "/lehrer/edit/{id}")
	public RedirectView findEntity(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		attributes.addFlashAttribute("add", false);
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("lehrer", lehrerService.find(id, locale));
		return new RedirectView("/lehrer");
	}

	@RequestMapping(value = "/lehrer/delete/{id}")
	public RedirectView delete(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		lehrerService.delete(lehrerService.find(id, locale));
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/lehrer");
	}

	private ModelAndView homeMav(ModelAndView mav) {
		mav.addObject("listLehrer", lehrerService.findAll());
		mav.addObject("listSchulfach", schulfachService.findAll());
		return mav;
	}

	private Lehrer setBenutzerRolle(Lehrer lehrer, final Locale locale) {
		Benutzer benutzer = new Benutzer();
		benutzer.setRolle(rolleService.find("ROLE_LEHRER", locale));
		lehrer.setBenutzer(benutzer);
		return lehrer;
	}
}
