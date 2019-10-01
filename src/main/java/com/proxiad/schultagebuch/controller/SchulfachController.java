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

import com.proxiad.schultagebuch.entity.Schulfach;
import com.proxiad.schultagebuch.service.SchulfachService;
import com.proxiad.schultagebuch.util.ValidierungsfehlerUtils;

@Controller
@Validated
public class SchulfachController {

	@Autowired
	private SchulfachService schulfachService;

	@RequestMapping(value = "/schulfach")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView showAllSchulfaecher() {
		return new ModelAndView("schulfachForm", "listSchulfach", schulfachService.findAll());
	}

	@RequestMapping(value = "/schulfach/add")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView addSchulfach(RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("schulfach", new Schulfach());
		return new RedirectView("/schulfach");
	}

	@RequestMapping(value = "/schulfach/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView editSchulfach(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		attributes.addFlashAttribute("add", false);
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("schulfach", schulfachService.find(id, locale));
		return new RedirectView("/schulfach");
	}

	@PostMapping(value = "/schulfach/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView saveSchulfach(RedirectAttributes attributes,
			@ModelAttribute(name = "schulfach") @Valid Schulfach schulfach, final BindingResult bindingResult) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		schulfachService.save(schulfach);
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/schulfach");
	}

	@RequestMapping(value = "/schulfach/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView deleteSchulfach(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		schulfachService.delete(schulfachService.find(id, locale));
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/schulfach");
	}
}
