package com.proxiad.schultagebuch.controller;

import java.util.List;

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

import com.proxiad.schultagebuch.entity.Schulfach;
import com.proxiad.schultagebuch.service.SchulfachService;
import com.proxiad.schultagebuch.util.ValidierungsfehlerUtils;

@Controller
@Validated
public class SchulfachController {

	@Autowired
	private SchulfachService schulfachService;

	@RequestMapping(value = "/schulfach")
	public ModelAndView home() {
		List<Schulfach> listSchulfach = schulfachService.findAll();
		return new ModelAndView("schulfachForm", "listSchulfach", listSchulfach);
	}

	@RequestMapping(value = "/schulfach/add")
	public RedirectView newEntity(RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("schulfach", new Schulfach());
		return new RedirectView("/schulfach");
	}

	@RequestMapping(value = "/schulfach/edit/{id}")
	public RedirectView findEntity(RedirectAttributes attributes, @PathVariable(value = "id") int id) {
		attributes.addFlashAttribute("add", false);
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("schulfach", schulfachService.find(id)
				.orElseThrow(() -> new IllegalArgumentException("Falsch schulfach id: " + id)));
		return new RedirectView("/schulfach");
	}

	@PostMapping(value = "/schulfach/add")
	public RedirectView save(RedirectAttributes attributes,
			@ModelAttribute(name = "schulfach") @Valid Schulfach schulfach, final BindingResult bindingResult) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		schulfachService.save(schulfach);
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/schulfach");
	}

	@RequestMapping(value = "/schulfach/delete/{id}")
	public RedirectView delete(RedirectAttributes attributes, @PathVariable(value = "id") int id) {
		schulfachService.delete(
				schulfachService.find(id).orElseThrow(() -> new IllegalArgumentException("Falsch schulfach id:" + id)));
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/schulfach");
	}
}
