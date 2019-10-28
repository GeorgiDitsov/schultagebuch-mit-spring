package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Klasse;
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

	public List<Schulstunde> findeAlle() {
		return repo.findAllByOrderByKlasseIdAscIdAsc();
	}

	public Schulstunde finden(final Long id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.course", new Object[] { id }, locale)));
	}

	public Schulstunde findeLehrerSchulstunde(final Long id, final Lehrer lehrer, final Locale locale) {
		return repo.findById(id).filter(schulstunde -> schulstunde.getLehrer().equals(lehrer))
				.orElseThrow(() -> new IllegalArgumentException(
						messageSource.getMessage("invalid.teacher.subject.relation", null, locale)));
	}

	public List<Schulstunde> findeDurchLehrer(final Lehrer lehrer) {
		return repo.findByLehrerOrderByKlasseIdAscIdAsc(lehrer);
	}

	public List<Schulstunde> findeDurchKlasse(final Klasse klasse) {
		return repo.findByKlasseOrderByIdAsc(klasse);
	}

	public void speichern(final Schulstunde schulstunde) {
		repo.save(schulstunde);
	}

	public void loeschen(final Long id) {
		repo.deleteById(id);
	}
}
