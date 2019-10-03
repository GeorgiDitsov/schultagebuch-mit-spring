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
public class ElternteilController extends AbstraktController {

	@Autowired
	private ElternteilService elternteilService;

	@Autowired
	private SchulerService schulerService;

	@RequestMapping(value = "/elternteil")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView alleElternZeigen() {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("listElternteil", elternteilService.findAll());
		attributes.put("listSchuler", schulerService.findAll());
		return super.ansicht("elternteilForm", attributes);
	}

	@RequestMapping(value = "/elternteil/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehendesElternteil(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("elternteil", elternteilService.find(id, locale));
		return super.umleiten("/elternteil");
	}

	@PostMapping(value = "/elternteil/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView elternteilSpeichern(RedirectAttributes attributes, @RequestHeader final String referer,
			@ModelAttribute(name = "elternteil") @Valid Elternteil elternteil, final BindingResult bindingResult) {
		ValidierungsfehlerUtils.fehlerPruefen(bindingResult);
		elternteilService.save(elternteil);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/elternteil");
	}

	@RequestMapping(value = "/elternteil/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView elternteilLoeschen(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		elternteilService.delete(elternteilService.find(id, locale));
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/elternteil");
	}

}
