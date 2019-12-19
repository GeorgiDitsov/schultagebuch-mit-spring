package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.entity.Semester;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.konstanten.StringKonstanten;
import com.proxiad.schultagebuch.repository.NoteRepository;

@Service
@Transactional
public class NoteService {

	@Autowired
	private NoteRepository repo;

	public List<Note> findeAlle() {
		return repo.findAllByOrderByNoteUpdateDatumDesc();
	}

	public Note finden(final Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new EntityNichtGefundenException("grade.not.found", new Object[] { id }));
	}

	public List<Note> findeSchulerNoten(final Schuler schuler, final Semester semester) {
		return repo.findBySchulerAndNoteUpdateDatumBetweenOrderByNoteUpdateDatumDesc(schuler,
				semester.getSemesterbeginn(), semester.getSemesterende());
	}

	public List<Note> findeSchulerNotenDurchSchulstunde(final Schuler schuler, final Schulstunde schulstunde,
			final Semester semester) {
		return repo.findBySchulerAndSchulstundeAndNoteUpdateDatumBetween(schuler, schulstunde,
				semester.getSemesterbeginn(), semester.getSemesterende());
	}

	public String findeSchulerLetzteNote(final Schuler schuler, final Locale locale) {
		return repo.findFirstBySchulerOrderByNoteUpdateDatumDesc(schuler)
				.map(note -> note.toNoteViewModell(locale).getKennzeichen())
				.orElse(StringKonstanten.OBJEKT_NICHT_VERFUEGBAR);
	}

	public void speichern(final Note note) {
		repo.save(note);
	}

	public void loeschen(final Long id) {
		repo.deleteById(id);
	}

}
