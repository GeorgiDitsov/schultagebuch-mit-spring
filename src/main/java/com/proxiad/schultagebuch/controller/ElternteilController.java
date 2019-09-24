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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.service.ElternteilService;
import com.proxiad.schultagebuch.service.SchulerService;
import com.proxiad.schultagebuch.util.ValidierungsfehlerUtils;

@Controller
@Validated
public class ElternteilController {

	@Autowired
	private ElternteilService elternteilService;

	@Autowired
	private SchulerService schulerService;

	@RequestMapping(value = "/elternteil")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("elternteilForm");
		mav.addObject("listElternteil", elternteilService.findAll());
		mav.addObject("listSchuler", schulerService.findAll());
		return mav;
	}

	@RequestMapping(value = "/elternteil/edit/{id}")
	public RedirectView findEntity(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("elternteil", elternteilService.find(id, locale));
		return new RedirectView("/elternteil");
	}

	@PostMapping(value = "/elternteil/save")
	public RedirectView save(RedirectAttributes attributes, @RequestHeader String referer,
			@ModelAttribute(name = "elternteil") @Valid Elternteil elternteil, final BindingResult bindingResult) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		elternteilService.save(elternteil);
		attributes.addFlashAttribute("successful", true);
		return new RedirectView(referer);
	}

	@RequestMapping(value = "/elternteil/delete/{id}")
	public RedirectView delete(RedirectAttributes attributes, @PathVariable(value = "id") final int id,
			final Locale locale) {
		elternteilService.delete(elternteilService.find(id, locale));
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/elternteil");
	}

}
