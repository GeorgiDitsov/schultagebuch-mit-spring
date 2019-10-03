package com.proxiad.schultagebuch.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.repository.NoteRepository;
import com.proxiad.schultagebuch.view.KindViewModel;
import com.proxiad.schultagebuch.view.NoteViewModel;

@Service
@Transactional
public class NoteService {

	private static final String DATE_TIME_PATTERN = "dd-MMM-yyyy HH:mm:ss";

	@Autowired
	private NoteRepository repo;

	public void save(final Note note) {
		repo.save(note);
	}

	public List<NoteViewModel> findNoteViewModelBySchuler(final Schuler schuler) {
		List<NoteViewModel> notenView = new ArrayList<>();
		repo.findBySchulerOrderByDatumDesc(schuler).stream().forEach(note -> {
			notenView.add(getNoteViewModel(note));
		});
		return notenView;
	}

	public void delete(final Note note) {
		repo.delete(note);
	}

	public List<KindViewModel> getKinderViewModel(final Elternteil elternteil, final Locale locale) {
		List<KindViewModel> kinderList = new ArrayList<>();
		elternteil.getKinder().stream().forEach(kind -> kinderList
				.add(new KindViewModel(kind.getId(), kind.getKennzeichen(), getSchulerLetzteNote(kind, locale))));
		return kinderList;
	}

	private String getSchulerLetzteNote(final Schuler schuler, final Locale locale) {
		Optional<Note> optionalNote = repo.findFirstBySchulerOrderByDatumDesc(schuler);
		if (optionalNote.isPresent()) {
			Note note = optionalNote.get();
			return note.getSchulstunde().getSchulfach().getName() + ", " + note.getSchulstunde().getLehrer().getName()
					+ ", " + note.getWert() + ", "
					+ note.getDatum().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN, locale));
		}
		return "n/a";
	}

	private NoteViewModel getNoteViewModel(final Note note) {
		return new NoteViewModel(note.getSchulstunde().getSchulfach().getName(),
				note.getSchulstunde().getLehrer().getName(), note.getWert(), note.getDatum());
	}
}
