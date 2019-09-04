package com.proxiad.schultagebuch.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.service.KlasseService;
import com.proxiad.schultagebuch.service.SchulerService;

@Controller
public class SchulerController {

	@Autowired
	private SchulerService schulerService;

	@Autowired
	private KlasseService klasseService;

	@RequestMapping(value = "/schuler")
	public ModelAndView home() {
		List<Schuler> listSchuler = schulerService.findAll();
		ModelAndView mav = new ModelAndView("schulerForm", "listSchuler", listSchuler);
		List<Klasse> listKlasse = klasseService.findAll();
		mav.addObject("listKlasse", listKlasse);
		return mav;
	}

	@RequestMapping(value = "/schuler/add")
	public RedirectView newEntity(RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("schuler", new Schuler());
		return new RedirectView("/schuler");
	}

	@RequestMapping(value = "/schuler/edit/{id}")
	public RedirectView findEntity(RedirectAttributes attributes, @PathVariable(value = "id") int id) {
		attributes.addFlashAttribute("add", false);
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("schuler",
				schulerService.find(id).orElseThrow(() -> new IllegalArgumentException("Falsch schuler mit id " + id)));
		return new RedirectView("/schuler");
	}

	@PostMapping(value = "/schuler/add")
	public RedirectView save(RedirectAttributes attributes, @ModelAttribute(name = "schuler") @Valid Schuler schuler) {
		schulerService.save(schuler);
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/schuler");
	}

	@RequestMapping(value = "/schuler/delete/{id}")
	public RedirectView delete(RedirectAttributes attributes, @PathVariable(value = "id") int id) {
		schulerService.delete(
				schulerService.find(id).orElseThrow(() -> new IllegalArgumentException("Falsch schuler mit id " + id)));
		attributes.addFlashAttribute("successfull", true);
		return new RedirectView("/schuler");
	}

}
