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
public class SchulfachController extends AbstraktController {

	@Autowired
	private SchulfachService schulfachService;

	@RequestMapping(value = "/schulfach")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView alleSchulfaecherZeigen() {
		return super.ansicht("schulfachForm", "listSchulfach", schulfachService.findAll());
	}

	@RequestMapping(value = "/schulfach/add")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView neuesSchulfach(RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("schulfach", new Schulfach());
		return super.umleiten("/schulfach");
	}

	@RequestMapping(value = "/schulfach/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehendesSchulfach(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", false);
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("schulfach", schulfachService.find(id, locale));
		return super.umleiten("/schulfach");
	}

	@PostMapping(value = "/schulfach/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView schulfachSpeichern(@ModelAttribute(name = "schulfach") @Valid Schulfach schulfach,
			final BindingResult bindingResult, RedirectAttributes attributes) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		schulfachService.save(schulfach);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/schulfach");
	}

	@RequestMapping(value = "/schulfach/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView schulfachLoeschen(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		schulfachService.delete(schulfachService.find(id, locale));
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/schulfach");
	}
}
