package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.repository.LehrerRepository;

@Service
@Transactional
public class LehrerService {

	@Autowired
	private LehrerRepository repo;

	@Autowired
	private MessageSource messageSource;

	public List<Lehrer> suche(final String lehrerName) {
		return repo.findByNameIgnoreCaseLikeOrderByIdAsc("%" + lehrerName + "%");
	}

	public List<Lehrer> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public Lehrer finden(final Long id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.teacher", new Object[] { id }, locale)));
	}

	public Lehrer findeDurchBenutzerName(final String benutzerName, final Locale locale) {
		return repo.findByBenutzerBenutzerName(benutzerName)
				.orElseThrow(() -> new UsernameNotFoundException(benutzerName));
	}

	public void speichern(final Lehrer lehrer) {
		repo.save(lehrer);
	}

	public void loeschen(final Long id) {
		repo.deleteById(id);
	}
}
