package com.proxiad.schultagebuch.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
public class SchulerController {

	@Autowired
	private SchulerService schulerService;

	@Autowired
	private KlasseService klasseService;

	@Autowired
	private ElternteilService elternteilService;

	@Autowired
	private RolleService rolleService;

	@RequestMapping(value = "/schuler")
	public ModelAndView home(final Locale locale) {
		return schulerMav(new ModelAndView("schulerForm"), locale);
	}

	@RequestMapping(value = "/schuler/add")
	public RedirectView newEntity(RedirectAttributes attributes, final Locale locale) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("schuler", PersonUtils.getNeuePerson(new Schuler(), () -> rolleService, locale));
		return new RedirectView("/schuler");
	}

	@RequestMapping(value = "/schuler/edit/{id}")
	public RedirectView findEntity(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		attributes.addFlashAttribute("add", false);
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("schuler", schulerService.find(id, locale));
		return new RedirectView("/schuler");
	}

	@PostMapping(value = "/schuler/save")
	public RedirectView save(RedirectAttributes attributes, @ModelAttribute(name = "schuler") @Valid Schuler schuler,
			final BindingResult bindingResult) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		schulerService.save(schuler);
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/schuler");
	}

	@RequestMapping(value = "/schuler/delete/{id}")
	public RedirectView delete(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		schulerService.delete(schulerService.find(id, locale));
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/schuler");
	}

	private ModelAndView schulerMav(ModelAndView mav, final Locale locale) {
		mav.addObject("listSchuler", schulerService.findAll());
		mav.addObject("listKlasse", klasseService.findAll());
		mav.addObject("listEltern", elternteilService.findAll());
		mav.addObject("elternteil", PersonUtils.getNeuePerson(new Elternteil(), () -> rolleService, locale));
		return mav;
	}

}
