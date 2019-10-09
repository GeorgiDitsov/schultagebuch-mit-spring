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
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.view.KindViewModel;
import com.proxiad.schultagebuch.view.NoteViewModel;

@Service
@Transactional
public class ViewModelService {

	private static final String DATE_TIME_PATTERN = "dd-MMM-yyyy HH:mm:ss";

	@Autowired
	private NoteService noteService;

	public List<NoteViewModel> getListNoteViewModelBySchuler(final Schuler schuler, final Locale locale) {
		List<NoteViewModel> noteViewModelsList = new ArrayList<>();
		noteService.findSchulerNoten(schuler).stream().forEach(note -> {
			noteViewModelsList.add(getNoteViewModel(note, locale));
		});
		return noteViewModelsList;
	}

	public List<NoteViewModel> getListNoteViewModelBySchulerUndSchulstunde(final Schuler schuler,
			final Schulstunde schulstunde, final Locale locale) {
		List<NoteViewModel> noteViewModelsList = new ArrayList<>();
		noteService.findSchulerNoten(schuler).stream().filter(note -> note.getSchulstunde().equals(schulstunde))
				.forEach(note -> {
					noteViewModelsList.add(getNoteViewModel(note, locale));
				});
		return noteViewModelsList;
	}

	public List<KindViewModel> getKinderViewModelle(final Elternteil elternteil, final Locale locale) {
		List<KindViewModel> kinderList = new ArrayList<>();
		elternteil.getKinder().stream().forEach(kind -> kinderList.add(schulerToKinderViewModel(kind, locale)));
		return kinderList;
	}

	public KindViewModel schulerToKinderViewModel(final Schuler schuler, final Locale locale) {
		return new KindViewModel(schuler.getId(), schuler.getKennzeichen(), getSchulerLetzteNote(schuler, locale),
				noteService.findSchulerNoten(schuler).stream().mapToDouble(Note::getWert).average().orElse(Double.NaN));
	}

	private String getSchulerLetzteNote(final Schuler schuler, final Locale locale) {
		Optional<Note> note = noteService.findSchulerLetzteNote(schuler);
		return note.isPresent() ? getNoteViewModel(note.get(), locale).getKennzeichen() : "n/a";
	}

	private NoteViewModel getNoteViewModel(final Note note, final Locale locale) {
		return new NoteViewModel(note.getId(), note.getSchulstunde().getSchulfach().getName(),
				note.getSchulstunde().getLehrer().getName(), note.getWert(),
				note.getDatum().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN, locale)));
	}
}
