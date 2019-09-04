package com.proxiad.schultagebuch.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.proxiad.schultagebuch.validator.annotation.SchulstundeConstraint;

@Controller
@Validated
public class SchulstundeController {

	@Autowired
	private SchulstundeService schulstundeService;

	@Autowired
	private KlasseService klasseService;

	@Autowired
	private SchulfachService schulfachService;

	@Autowired
	private LehrerService lehrerService;

	@RequestMapping(value = "/schulstunde")
	public ModelAndView home() {
		List<Schulstunde> listSchulstunde = schulstundeService.findAll();
		ModelAndView mav = new ModelAndView("schulstundeForm", "listSchulstunde", listSchulstunde);
		mav.addObject("listKlasse", klasseService.findAll());
		mav.addObject("listSchulfach", schulfachService.findAll());
		mav.addObject("listLehrer", lehrerService.findAll());
		return mav;
	}

	@RequestMapping(value = "/schulstunde/add")
	public RedirectView emptyEntity(RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("schulstunde", new Schulstunde());
		return new RedirectView("/schulstunde");
	}

	@RequestMapping(value = "/schulstunde/edit/{id}")
	public RedirectView findEntity(RedirectAttributes attributes, @PathVariable(value = "id") int id) {
		attributes.addFlashAttribute("add", false);
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("schulstunde", schulstundeService.find(id)
				.orElseThrow(() -> new IllegalArgumentException("Falsch schulstunde id " + id)));
		return new RedirectView("/schulstunde");
	}

	@PostMapping(value = "/schulstunde/add")
	public RedirectView save(RedirectAttributes attributes,
			@ModelAttribute(name = "schulstunde") @Valid @SchulstundeConstraint Schulstunde schulstunde) {
		schulstundeService.save(schulstunde);
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/schulstunde");
	}

	@RequestMapping(value = "/schulstunde/delete/{id}")
	public RedirectView delete(RedirectAttributes attributes, @PathVariable(value = "id") int id) {
		schulstundeService.delete(schulstundeService.find(id)
				.orElseThrow(() -> new IllegalArgumentException("Falsch schulstunde id: " + id)));
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/schulstunde");
	}
}
