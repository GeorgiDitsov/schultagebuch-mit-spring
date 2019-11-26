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

import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.service.SemesterService;
import com.proxiad.schultagebuch.service.ViewModelService;

@Controller
@Validated
public class SemesterController extends AbstraktController {

	@Autowired
	private SemesterService semesterService;

	@Autowired
	private ViewModelService viewModelService;

	@RequestMapping(value = "/semester")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView alleSemesterAnzeigen(final Locale locale) {
		return super.ansicht("semesterForm", "listSemester", viewModelService.getListerDerSemesterViewModelle(locale));
	}

	@RequestMapping(value = "/semester/add")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView neuesSemester(@RequestHeader final String referer, final RedirectAttributes attributes) {
		modalAttributes("add", new Semester(), attributes);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/semester/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView bestehendesSemester(@RequestHeader final String referer,
			@PathVariable(value = "id") final int id, final RedirectAttributes attributes) {
		modalAttributes("edit", semesterService.finde(id), attributes);
		return super.umleiten(referer);
	}

	@PostMapping(value = "/semester/save")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView semesterSpeichern(@RequestHeader final String referer,
			@ModelAttribute(name = "semester") @Valid Semester semester, final BindingResult bindingResult,
			final RedirectAttributes attributes) {
		semesterService.speichern(semester);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	@RequestMapping(value = "/semester/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RedirectView semesterLoeschen(@RequestHeader final String referer, @PathVariable(value = "id") final int id,
			final RedirectAttributes attributes) {
		semesterService.loeschen(id);
		attributes.addFlashAttribute("successful", true);
		return super.umleiten(referer);
	}

	private void modalAttributes(final String modalType, final Semester semester, final RedirectAttributes attributes) {
		attributes.addFlashAttribute(modalType, true);
		attributes.addFlashAttribute("semester", semester);
	}

}
