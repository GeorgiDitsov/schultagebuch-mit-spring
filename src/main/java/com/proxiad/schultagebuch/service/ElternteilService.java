package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.repository.ElternteilRepository;

@Service
@Transactional
public class ElternteilService {

	@Autowired
	private ElternteilRepository repo;

	@Autowired
	private MessageSource messageSource;

	public List<Elternteil> suche(final String elternteilName) {
		return repo.findByNameIgnoreCaseLikeOrderByIdAsc("%" + elternteilName + "%");
	}

	public List<Elternteil> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public Elternteil elternteilFinde(final Long id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.parent", new Object[] { id }, locale)));
	}

	public Elternteil findeNachBenutzerName(final String benutzerName, final Locale locale) {
		return repo.findByBenutzerBenutzerName(benutzerName)
				.orElseThrow(() -> new UsernameNotFoundException(benutzerName));
	}

	public void speichern(final Elternteil elternteil) {
		repo.save(elternteil);
	}

	public void loeschen(final Long id) {
		repo.deleteById(id);
	}

}
