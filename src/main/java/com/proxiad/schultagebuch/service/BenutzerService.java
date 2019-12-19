package com.proxiad.schultagebuch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.BenutzerRepository;
import com.proxiad.schultagebuch.util.SuchenUtils;

@Service
@Transactional
public class BenutzerService {

	@Autowired
	private BenutzerRepository repo;

	public List<Benutzer> suchen(final String benutzername) {
		return repo.findByBenutzernameIgnoreCaseLikeOrderByIdAsc(SuchenUtils.suchenNach(benutzername));
	}

	public List<Benutzer> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public Benutzer finden(final Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntityNichtGefundenException("user.not.found", new Object[] { id }));
	}

	public Benutzer findeDurchBenutzername(final String benutzername) {
		return repo.findByBenutzername(benutzername).orElseThrow(() -> new UsernameNotFoundException(benutzername));
	}

	public void speichern(final Benutzer benutzer) {
		repo.save(benutzer);
	}

	public void loeschen(final Long id) {
		repo.deleteById(id);
	}

}
