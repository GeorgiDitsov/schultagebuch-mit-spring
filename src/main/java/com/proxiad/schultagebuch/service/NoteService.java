package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.repository.NoteRepository;

@Service
@Transactional
public class NoteService {

	@Autowired
	private NoteRepository repo;

	@Autowired
	private MessageSource messageSource;

	public Note finden(final int id, final Locale locale) {
		return repo.findById(id).orElseThrow(
				() -> new IllegalArgumentException(messageSource.getMessage("invalid.grade", new Object[id], locale)));
	}

	public Optional<Note> findSchulerLetzteNote(final Schuler schuler) {
		return repo.findFirstBySchulerOrderByDatumDesc(schuler);
	}

	public List<Note> findSchulerNoten(final Schuler schuler) {
		return repo.findBySchulerOrderByDatumDesc(schuler);
	}

	public void speichern(final Note note) {
		repo.save(note);
	}

	public void loeschen(final Note note) {
		repo.delete(note);
	}

}