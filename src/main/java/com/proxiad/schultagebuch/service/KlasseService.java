package com.proxiad.schultagebuch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.KlasseRepository;

@Service
@Transactional
public class KlasseService {

	@Autowired
	private KlasseRepository repo;

	public List<Klasse> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public Klasse finden(final Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntityNichtGefundenException("class.not.found", new Object[] { id }));
	}

	public void speichern(final Klasse klasse) {
		repo.save(klasse);
	}

	public void loeschen(final Long id) {
		repo.deleteById(id);
	}

}
