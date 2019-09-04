package com.proxiad.schultagebuch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.service.KlasseService;
import com.proxiad.schultagebuch.util.KlasseBauen;
import com.proxiad.schultagebuch.validator.annotation.KlasseNameConstraint;

@Controller
@Validated
public class KlasseController {

	@Autowired
	private KlasseService klasseService;

	@RequestMapping(value = "/klasse")
	public ModelAndView home() {
		List<Klasse> listKlasse = klasseService.findAll();
		return new ModelAndView("klasseForm", "listKlasse", listKlasse);
	}

	@RequestMapping(value = "/klasse/add")
	public RedirectView emptyEntity(RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("klasse", new Klasse());
		return new RedirectView("/klasse");
	}

	@RequestMapping(value = "/klasse/edit/{id}")
	public RedirectView findForEdit(RedirectAttributes attributes, @PathVariable(value = "id") int id) {
		attributes.addFlashAttribute("add", false);
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("klasse",
				klasseService.find(id).orElseThrow(() -> new IllegalArgumentException("Falsch klasse id: " + id)));
		return new RedirectView("/klasse");
	}

	@PostMapping(value = "/klasse/add")
	public RedirectView save(RedirectAttributes attributes,
			@RequestParam(name = "klasseName") @KlasseNameConstraint String klasseName,
			@ModelAttribute(name = "klasse") Klasse klasse) {
		klasseService.save(KlasseBauen.getInstance().bauden(klasseName, klasse));
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/klasse");
	}

	@RequestMapping(value = "/klasse/delete/{id}")
	public RedirectView delete(RedirectAttributes attributes, @PathVariable(value = "id") int id) {
		klasseService.delete(
				klasseService.find(id).orElseThrow(() -> new IllegalArgumentException("Falsch klasse id: " + id)));
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/klasse");
	}

}
