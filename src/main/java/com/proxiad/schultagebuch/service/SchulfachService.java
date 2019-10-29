package com.proxiad.schultagebuch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Schulfach;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.SchulfachRepository;

@Service
@Transactional
public class SchulfachService {

	@Autowired
	private SchulfachRepository repo;

	public List<Schulfach> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public Schulfach finden(final Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntityNichtGefundenException("subject.not.found", new Object[] { id }));
	}

	public void speichern(final Schulfach schulfach) {
		repo.save(schulfach);
	}

	public void loeschen(final Long id) {
		repo.deleteById(id);
	}

}
