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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.proxiad.schultagebuch.entity.Schulfach;
import com.proxiad.schultagebuch.service.SchulfachService;
import com.proxiad.schultagebuch.util.ValidierungUtils;

@Controller
@Validated
public class SchulfachController extends AbstraktController {

	@Autowired
	private SchulfachService schulfachService;

	@RequestMapping(value = "/schulfach")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView alleSchulfaecherAnzeigen() {
		return super.ansicht("schulfachForm", "listSchulfach", schulfachService.findeAlle());
	}

	@RequestMapping(value = "/schulfach/add")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView neuesSchulfach(@RequestHeader final String referer, RedirectAttributes attributes) {
		modalAttributes("add", new Schulfach(), attributes);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/schulfach/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehendesSchulfach(@RequestHeader final String referer,
			@PathVariable(value = "id") final Long id, final Locale locale, RedirectAttributes attributes) {
		modalAttributes("edit", schulfachService.finden(id, locale), attributes);
		return super.umleiten(referer);
	}

	@PostMapping(value = "/schulfach/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView schulfachSpeichern(@RequestHeader final String referer,
			@ModelAttribute(name = "schulfach") @Valid Schulfach schulfach, final BindingResult bindingResult,
			RedirectAttributes attributes) {
		ValidierungUtils.fehlerPruefen(bindingResult);
		schulfachService.speichern(schulfach);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/schulfach/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView schulfachLoeschen(@RequestHeader final String referer,
			@PathVariable(value = "id") final Long id, final Locale locale, RedirectAttributes attributes) {
		schulfachService.loeschen(id);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	private void modalAttributes(final String modalType, final Schulfach schulfach, RedirectAttributes attributes) {
		attributes.addFlashAttribute(modalType, true);
		attributes.addFlashAttribute("schulfach", schulfach);
	}
}
