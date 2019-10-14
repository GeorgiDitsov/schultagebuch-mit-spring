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

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.service.ElternteilService;
import com.proxiad.schultagebuch.service.SchulerService;
import com.proxiad.schultagebuch.util.ValidierungUtils;

@Controller
@Validated
public class ElternteilController extends AbstraktController {

	@Autowired
	private ElternteilService elternteilService;

	@Autowired
	private SchulerService schulerService;

	@RequestMapping(value = "/elternteil")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView alleElternAnzeigen() {
		return super.ansicht("elternteilForm", "listElternteil", elternteilService.findeAlle());
	}

	@RequestMapping(value = "/elternteil/search")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView gefundenElternAnzeigen(@ModelAttribute(name = "string") final String elternteilName) {
		return super.ansicht("elternteilForm", "listElternteil", elternteilService.suche(elternteilName));
	}

	@RequestMapping(value = "/elternteil/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehendesElternteil(@RequestHeader final String referer,
			@PathVariable(value = "id") final int id, final Locale locale, RedirectAttributes attributes) {
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("listSchuler", schulerService.findeAlle());
		attributes.addFlashAttribute("elternteil", elternteilService.elternteilFinde(id, locale));
		return super.umleiten(referer);
	}

	@PostMapping(value = "/elternteil/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView elternteilSpeichern(@ModelAttribute(name = "elternteil") @Valid Elternteil elternteil,
			final BindingResult bindingResult, RedirectAttributes attributes) {
		ValidierungUtils.fehlerPruefen(bindingResult);
		elternteilService.speichern(elternteil);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/elternteil");
	}

	@RequestMapping(value = "/elternteil/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView elternteilLoeschen(@RequestHeader final String referer,
			@PathVariable(value = "id") final int id, final Locale locale, RedirectAttributes attributes) {
		elternteilService.loeschen(elternteilService.elternteilFinde(id, locale));
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

}
