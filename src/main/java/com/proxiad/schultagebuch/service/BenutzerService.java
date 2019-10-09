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

	@Autowired
	private BenutzerRepository repo;

	@Autowired
	private MessageSource messageSource;

	public List<Benutzer> suchen(final String benutzerName) {
		return repo.findByBenutzerNameIgnoreCaseLikeOrderByIdAsc("%" + benutzerName + "%");
	}

	public void speichern(final Benutzer benutzer) {
		repo.save(benutzer);
	}

	public List<Benutzer> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public Benutzer finden(final int id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.user", new Object[] { id }, locale)));
	}

	public Benutzer findeNachBenutzerName(final String benutzername) {
		return repo.findByBenutzerName(benutzername).orElseThrow(() -> new UsernameNotFoundException(benutzername));
	}

	public void loeschen(final Benutzer benutzer) {
		repo.delete(benutzer);
	}

}
