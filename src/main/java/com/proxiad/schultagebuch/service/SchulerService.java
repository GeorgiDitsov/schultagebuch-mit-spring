package com.proxiad.schultagebuch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.exception.EntityUngueltigeRelationException;
import com.proxiad.schultagebuch.repository.SchulerRepository;
import com.proxiad.schultagebuch.util.SuchenUtils;

@Service
@Transactional
public class SchulerService {

	@Autowired
	private SchulerRepository repo;

	public List<Schuler> suche(final String schulerName) {
		return repo.findByNameIgnoreCaseLikeOrderByIdAsc(SuchenUtils.suchenNach(schulerName));
	}

	public List<Schuler> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public List<Schuler> findeAlleSchulernImKlasse(final Klasse klasse) {
		return repo.findByKlasseOrderByIdAsc(klasse);
	}

	public Schuler finden(final Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntityNichtGefundenException("student.not.found", new Object[] { id }));
	}

	public Schuler findeElternteilKind(final Long schulerId, final Elternteil elternteil) {
		return repo.findById(schulerId).filter(kind -> elternteil.getKinder().contains(kind))
				.orElseThrow(() -> new EntityUngueltigeRelationException("invalid.parent.student.relation"));
	}

	public Schuler findeDurchSchulstunde(final Long schulerId, final Schulstunde schulstunde) {
		return repo.findById(schulerId).filter(schuler -> schulstunde.getKlasse().getSchulerSet().contains(schuler))
				.orElseThrow(() -> new EntityUngueltigeRelationException("invalid.student.course.relation"));
	}

	public Schuler findeDurchBenutzerName(final String benutzerName) {
		return repo.findByBenutzerBenutzerName(benutzerName)
				.orElseThrow(() -> new UsernameNotFoundException(benutzerName));
	}

	public void speichern(final Schuler schuler) {
		repo.save(schuler);
	}

	public void loeschen(final Long id) {
		repo.deleteById(id);
	}

}
