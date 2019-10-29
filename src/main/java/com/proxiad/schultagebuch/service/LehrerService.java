package com.proxiad.schultagebuch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.LehrerRepository;
import com.proxiad.schultagebuch.util.SuchenUtils;

@Service
@Transactional
public class LehrerService {

	@Autowired
	private LehrerRepository repo;

	public List<Lehrer> suche(final String lehrerName) {
		return repo.findByNameIgnoreCaseLikeOrderByIdAsc(SuchenUtils.suchenNach(lehrerName));
	}

	public List<Lehrer> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public Lehrer finden(final Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntityNichtGefundenException("teacher.not.found", new Object[] { id }));
	}

	public Lehrer findeDurchBenutzerName(final String benutzerName) {
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
