package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.repository.KlasseRepository;

@Service
@Transactional
public class KlasseService {

	@Autowired
	private KlasseRepository repo;

	@Autowired
	private MessageSource messageSource;

	public List<Klasse> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public Klasse finden(final Long id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.class", new Object[] { id }, locale)));
	}

	public void speichern(final Klasse klasse) {
		repo.save(klasse);
	}

	public void loeschen(final Long id) {
		repo.deleteById(id);
	}

}
