package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.repository.BenutzerRepository;

@Service
@Transactional
public class BenutzerService {

	@Autowired
	private BenutzerRepository repo;

	@Autowired
	private MessageSource messageSource;

	public void save(Benutzer benutzer) {
		repo.save(benutzer);
	}

	public List<Benutzer> findAll() {
		return repo.findAllByOrderByIdAsc();
	}

	public Benutzer find(int id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.user", new Object[] { id }, locale)));
	}

	public void delete(Benutzer benutzer) {
		repo.delete(benutzer);
	}

	public Optional<Benutzer> findByBenutzerName(String benutzername) {
		return repo.findByBenutzerName(benutzername);
	}
}
