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

	public void save(Klasse klasse) {
		repo.save(klasse);
	}

	public List<Klasse> findAll() {
		return repo.findAllByOrderByIdAsc();
	}

	public Klasse find(int id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.class", new Object[] { id }, locale)));
	}

	public void delete(Klasse klasse) {
		repo.delete(klasse);
	}

}
