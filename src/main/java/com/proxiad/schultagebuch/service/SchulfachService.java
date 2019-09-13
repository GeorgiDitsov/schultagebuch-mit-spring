package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Schulfach;
import com.proxiad.schultagebuch.repository.SchulfachRepository;

@Service
@Transactional
public class SchulfachService {

	@Autowired
	private SchulfachRepository repo;

	@Autowired
	private MessageSource messageSource;

	public void save(Schulfach schulfach) {
		repo.save(schulfach);
	}

	public List<Schulfach> findAll() {
		return repo.findAllByOrderByIdAsc();
	}

	public Schulfach find(int id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.subject", new Object[] { id }, locale)));
	}

	public void delete(Schulfach schulfach) {
		repo.delete(schulfach);
	}
}
