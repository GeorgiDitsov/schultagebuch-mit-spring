package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.repository.SemesterRepository;
import com.proxiad.schultagebuch.util.ValidierungUtils;

@Service
@Transactional
public class SemesterService {

	@Autowired
	private SemesterRepository repo;

	@Autowired
	private MessageSource messageSource;

	public void speichern(@Valid final Semester semester, final BindingResult bindingResult) {
		ValidierungUtils.fehlerPruefen(bindingResult);
		repo.save(semester);
	}

	public List<Semester> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public Semester finde(final int id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.semester", new Object[id], locale)));
	}

	public void loeschen(final Semester semester) {
		repo.delete(semester);
	}
}
