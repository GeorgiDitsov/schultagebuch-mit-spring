package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.repository.SchulerRepository;

@Service
@Transactional
public class SchulerService {

	@Autowired
	private SchulerRepository repo;

	@Autowired
	private MessageSource messageSource;

	public void save(Schuler schuler) {
		repo.save(schuler);
	}

	public List<Schuler> findAll() {
		return repo.findAllByOrderByIdAsc();
	}

	public Schuler find(int id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.student", new Object[] { id }, locale)));
	}

	public Schuler findByBenutzerName(final String benutzerName, final Locale locale) {
		return repo.findByBenutzerBenutzerName(benutzerName)
				.orElseThrow(() -> new UsernameNotFoundException(benutzerName));
	}

	public void delete(Schuler schuler) {
		repo.delete(schuler);
	}

}
