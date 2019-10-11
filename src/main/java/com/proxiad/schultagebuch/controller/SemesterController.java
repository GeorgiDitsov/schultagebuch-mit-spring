package com.proxiad.schultagebuch.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.service.SemesterService;
import com.proxiad.schultagebuch.util.SemesterUtils;
import com.proxiad.schultagebuch.util.ValidierungUtils;

@Controller
@Validated
public class SemesterController extends AbstraktController {

	@Autowired
	private SemesterService semesterService;

	@RequestMapping(value = "/semester")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView alleSemesterAnzeigen() {
		return super.ansicht("semesterForm", "listSemester", semesterService.findeAlle());
	}

	@RequestMapping(value = "/semester/add")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView neuesSemester(RedirectAttributes attributes) {
		modalAttributes("add", new Semester(), attributes);
		return super.umleiten("/semester");
	}

	@RequestMapping(value = "/semester/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehendesSemester(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		modalAttributes("edit", semesterService.finde(id, locale), attributes);
		return super.umleiten("/semester");
	}

	@PostMapping(value = "/semester/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView semesterSpeichern(@RequestParam(name = "begin") final String semesterbeginn,
			@RequestParam(name = "end") final String semesterende, @ModelAttribute(name = "semester") Semester semester,
			final BindingResult bindingResult, RedirectAttributes attributes) {
		ValidierungUtils.fehlerPruefen(bindingResult);
		semesterService.speichern(SemesterUtils.setSemesterDatum(semester, semesterbeginn, semesterende),
				bindingResult);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/semester");
	}

	@RequestMapping(value = "/semester/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView semesterLoeschen(@PathVariable(value = "id") final int id, final Locale locale,
			RedirectAttributes attributes) {
		semesterService.loeschen(semesterService.finde(id, locale));
		attributes.addFlashAttribute("successful", true);
		return super.umleiten("/semester");
	}

	private void modalAttributes(final String modalType, final Semester semester, RedirectAttributes attributes) {
		attributes.addFlashAttribute(modalType, true);
		attributes.addFlashAttribute("semester", semester);
	}

}
