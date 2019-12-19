package com.proxiad.schultagebuch.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.SemesterRepository;

@Service
@Transactional
public class SemesterService {

	@Autowired
	private SemesterRepository repo;

	public List<Semester> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public Semester finde(final int id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntityNichtGefundenException("semester.not.found", new Object[] { id }));
	}

	public Semester findeAktuelleSemester() {
		return repo.findBySemesterbeginnBeforeAndSemesterendeAfter(LocalDateTime.now()).orElseThrow(
				() -> new EntityNichtGefundenException("semester.not.found.for.current.date", new Object[] {}));
	}

	public void speichern(final Semester semester) {
		repo.save(semester);
	}

	public void loeschen(final int id) {
		repo.deleteById(id);
	}
}
