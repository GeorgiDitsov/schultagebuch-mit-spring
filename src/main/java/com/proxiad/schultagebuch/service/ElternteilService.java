package com.proxiad.schultagebuch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.ElternteilRepository;
import com.proxiad.schultagebuch.util.SuchenUtils;

@Service
@Transactional
public class ElternteilService {

	@Autowired
	private ElternteilRepository repo;

	public List<Elternteil> suche(final String elternteilName) {
		return repo.findByNameIgnoreCaseLikeOrderByIdAsc(SuchenUtils.suchenNach(elternteilName));
	}

	public List<Elternteil> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public Elternteil finden(final Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntityNichtGefundenException("parent.not.found", new Object[] { id }));
	}

	public Elternteil findeDurchBenutzername(final String benutzername) {
		return repo.findByBenutzerBenutzername(benutzername)
				.orElseThrow(() -> new UsernameNotFoundException(benutzername));
	}

	public void speichern(final Elternteil elternteil) {
		repo.save(elternteil);
	}

	public void loeschen(final Long id) {
		repo.deleteById(id);
	}

}
