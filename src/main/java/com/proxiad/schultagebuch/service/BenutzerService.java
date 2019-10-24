package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.repository.BenutzerRepository;

@Service
@Transactional
public class BenutzerService {

	private static final StringBuilder BENUTZER_LIKE = new StringBuilder("%%");

	@Autowired
	private BenutzerRepository repo;

	@Autowired
	private MessageSource messageSource;

	public List<Benutzer> suchen(final String benutzerName) {
		return repo.findByBenutzerNameIgnoreCaseLikeOrderByIdAsc(BENUTZER_LIKE.insert(1, benutzerName).toString());
	}

	public Benutzer finden(final Long id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.user", new Object[] { id }, locale)));
	}

	public Benutzer findeDurchBenutzerName(final String benutzername) {
		return repo.findByBenutzerName(benutzername).orElseThrow(() -> new UsernameNotFoundException(benutzername));
	}

	public List<Benutzer> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public void speichern(final Benutzer benutzer) {
		repo.save(benutzer);
	}

	public void loeschen(final Long id) {
		repo.deleteById(id);
	}

}
