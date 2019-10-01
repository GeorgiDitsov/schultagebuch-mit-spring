package com.proxiad.schultagebuch.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.service.ElternteilService;
import com.proxiad.schultagebuch.service.NoteService;
import com.proxiad.schultagebuch.service.SchulerService;
import com.proxiad.schultagebuch.util.PersonUtils;

@Controller
@Validated
public class NoteController {

	@Autowired
	private NoteService noteService;

	@Autowired
	private SchulerService schulerService;

	@Autowired
	private ElternteilService elternteilService;

	@RequestMapping(value = "/meine-noten")
	@PreAuthorize("hasRole('SCHULER')")
	public ModelAndView showSchulerNoten(final Principal principal, final Locale locale) {
		return schulerNotenView(new ModelAndView("notenForm"),
				(Schuler) PersonUtils.getPersonNachBenutzername(principal.getName(), schulerService, locale));
	}

	@RequestMapping(value = "/meine-kinder")
	@PreAuthorize("hasRole('ELTERNTEIL')")
	public ModelAndView showElternteilKinder(final Principal principal, final Locale locale) {
		return elternteilKinderView(new ModelAndView("notenForm"),
				(Elternteil) PersonUtils.getPersonNachBenutzername(principal.getName(), elternteilService, locale),
				locale);
	}

	@RequestMapping(value = "/meine-kinder/noten/{id}")
	@PreAuthorize("hasRole('ELTERNTEIL')")
	public RedirectView showElternteilKindNoten(@PathVariable(value = "id") final int id, final Principal principal,
			final Locale locale, RedirectAttributes attributes) {
		Elternteil elternteil = (Elternteil) PersonUtils.getPersonNachBenutzername(principal.getName(),
				elternteilService, locale);
		Schuler kind = schulerService.findElternteilKind(id, elternteil, locale);
		attributes.addFlashAttribute("showSchulerfolg", true);
		attributes.addFlashAttribute("kind", kind);
		attributes.addFlashAttribute("listNoten", noteService.findBySchuler(kind));
		return new RedirectView("/meine-kinder");
	}

	private ModelAndView schulerNotenView(ModelAndView view, final Schuler schuler) {
		view.addObject("schuler", schuler);
		view.addObject("listNoten", noteService.findBySchuler(schuler));
		return view;
	}

	private ModelAndView elternteilKinderView(ModelAndView view, final Elternteil elternteil, final Locale locale) {
		view.addObject("elternteil", elternteil);
		view.addObject("listKinder", noteService.getKinder(elternteil, locale));
		return view;
	}

}