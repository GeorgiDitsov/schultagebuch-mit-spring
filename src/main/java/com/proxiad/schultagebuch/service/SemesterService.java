package com.proxiad.schultagebuch.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.repository.SemesterRepository;
import com.proxiad.schultagebuch.util.DatumUtils;
import com.proxiad.schultagebuch.view.SemesterViewModel;

@Service
@Transactional
public class SemesterService {

	@Autowired
	private SemesterRepository repo;

	@Autowired
	private MessageSource messageSource;

	public void speichern(final Semester semester) {
		repo.save(semester);
	}

	public List<SemesterViewModel> findeAlleSemesterViewModelle(final Locale locale) {
		List<SemesterViewModel> list = new ArrayList<>();
		repo.findAllByOrderByIdAsc().stream()
				.forEach(semester -> list.add(new SemesterViewModel(semester.getId(),
						DatumUtils.localDateTimeZuString(semester.getSemesterbeginn(), locale),
						DatumUtils.localDateTimeZuString(semester.getSemesterende(), locale))));
		return list;
	}

	public Semester finde(final int id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.semester", new Object[id], locale)));
	}

	public Semester findeAktuelleSemester(final Locale locale) {
		return repo.findBySemesterbeginnBeforeAndSemesterendeAfter(LocalDateTime.now())
				.orElseThrow(() -> new IllegalArgumentException(
						messageSource.getMessage("invalid.semester.for.current.date", null, locale)));
	}

	public void loeschen(final Semester semester) {
		repo.delete(semester);
	}
}
