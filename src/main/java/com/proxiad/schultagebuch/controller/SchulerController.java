package com.proxiad.schultagebuch.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.service.ElternteilService;
import com.proxiad.schultagebuch.service.KlasseService;
import com.proxiad.schultagebuch.service.RolleService;
import com.proxiad.schultagebuch.service.SchulerService;

@Controller
public class SchulerController {

	@Autowired
	private SchulerService schulerService;

	@Autowired
	private KlasseService klasseService;

	@Autowired
	private ElternteilService elternteilService;

	@Autowired
	private RolleService rolleService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/schuler")
	public ModelAndView home() {
		return mav(new ModelAndView("schulerForm"));
	}

	@RequestMapping(value = "/schuler/search")
	public RedirectView search(RedirectAttributes attributes) {

		return new RedirectView("/schuler");
	}

	@RequestMapping(value = "/schuler/add")
	public RedirectView newEntity(RedirectAttributes attributes) {
		attributes.addFlashAttribute("add", true);
		attributes.addFlashAttribute("edit", false);
		attributes.addFlashAttribute("schuler", getNewSchuler());
		return new RedirectView("/schuler");
	}

	@RequestMapping(value = "/schuler/edit/{id}")
	public RedirectView findEntity(RedirectAttributes attributes, @PathVariable(value = "id") int id) {
		attributes.addFlashAttribute("add", false);
		attributes.addFlashAttribute("edit", true);
		attributes.addFlashAttribute("schuler", schulerService.find(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.student", new Object[] { id }, Locale.GERMANY))));
		return new RedirectView("/schuler");
	}

	@PostMapping(value = "/schuler/add")
	public RedirectView save(RedirectAttributes attributes, @ModelAttribute(name = "schuler") @Valid Schuler schuler) {
		schulerService.save(schuler);
		schuler.getEltern().stream().forEach(elternteil -> System.err.println(elternteil.getKennzeichen() + "\n"));
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/schuler");
	}

	@RequestMapping(value = "/schuler/delete/{id}")
	public RedirectView delete(RedirectAttributes attributes, @PathVariable(value = "id") int id) {
		Schuler schuler = schulerService.find(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.student", new Object[] { id }, Locale.GERMANY)));
		schulerService.delete(schuler);
		attributes.addFlashAttribute("successful", true);
		return new RedirectView("/schuler");
	}

	private ModelAndView mav(ModelAndView mav) {
		List<Schuler> listSchuler = schulerService.findAll();
		List<Klasse> listKlasse = klasseService.findAll();
		List<Elternteil> listEltern = elternteilService.findAll();
		mav.addObject("listSchuler", listSchuler);
		mav.addObject("listKlasse", listKlasse);
		mav.addObject("listEltern", listEltern);
		return mav;
	}

	private Schuler getNewSchuler() {
		Schuler schuler = new Schuler();
		Benutzer benutzer = new Benutzer();
		benutzer.setRolle(rolleService.find("ROLE_SCHULER").orElseThrow(
				() -> new IllegalArgumentException(messageSource.getMessage("invalid.role", null, Locale.GERMANY))));
		schuler.setBenutzer(benutzer);
		return schuler;
	}
}
