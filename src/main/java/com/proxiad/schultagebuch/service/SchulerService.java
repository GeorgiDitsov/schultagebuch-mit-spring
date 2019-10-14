package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.repository.SchulerRepository;

@Service
@Transactional
public class SchulerService {

	@Autowired
	private SchulerRepository repo;

	@Autowired
	private MessageSource messageSource;

	public List<Schuler> suche(final String schulerName) {
		return repo.findByNameIgnoreCaseLikeOrderByIdAsc("%" + schulerName + "%");
	}

	public void speichern(final Schuler schuler) {
		repo.save(schuler);
	}

	public List<Schuler> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public List<Schuler> schulernViewModelleFinden(final Klasse klasse) {
		return repo.findByKlasseOrderByIdAsc(klasse);
	}

	public Schuler finden(final int id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.student", new Object[] { id }, locale)));
	}

	public Schuler elternteilKindFinde(final int schulerId, final Elternteil elternteil, final Locale locale) {
		return repo.findById(schulerId).filter(kind -> elternteil.getKinder().contains(kind))
				.orElseThrow(() -> new IllegalArgumentException(
						messageSource.getMessage("invalid.parent.student.relation", null, locale)));
	}

	public Schuler schulerFindeNachSchulstunde(final int schulerId, final Schulstunde schulstunde,
			final Locale locale) {
		return repo.findById(schulerId).filter(schuler -> schulstunde.getKlasse().getSchulerSet().contains(schuler))
				.orElseThrow(() -> new IllegalArgumentException(
						messageSource.getMessage("invalid.student.course.relation", null, locale)));
	}

	public Schuler findeNachBenutzerName(final String benutzerName, final Locale locale) {
		return repo.findByBenutzerBenutzerName(benutzerName)
				.orElseThrow(() -> new UsernameNotFoundException(benutzerName));
	}

	public void loeschen(final Schuler schuler) {
		repo.delete(schuler);
	}

}
