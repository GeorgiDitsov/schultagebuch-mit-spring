package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.repository.SchulstundeRepository;

@Service
@Transactional
public class SchulstundeService {

	@Autowired
	private SchulstundeRepository repo;

	@Autowired
	private MessageSource messageSource;

	public void save(Schulstunde schulstunde) {
		repo.save(schulstunde);
	}

	public List<Schulstunde> findAll() {
		return repo.findAllByOrderByKlasseIdAscIdAsc();
	}

	public Schulstunde find(final int id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.course", new Object[] { id }, locale)));
	}

	public List<Schulstunde> findByLehrer(final Lehrer lehrer) {
		return repo.findByLehrerOrderByKlasseIdAscIdAsc(lehrer);
	}

	public void delete(Schulstunde schulstunde) {
		repo.delete(schulstunde);
	}
}
