package com.proxiad.schultagebuch.service;

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
import com.proxiad.schultagebuch.util.BerechnungUtils;
import com.proxiad.schultagebuch.util.NoteUtils;
import com.proxiad.schultagebuch.view.KindViewModel;
import com.proxiad.schultagebuch.view.NoteViewModel;

@Service
@Transactional
public class ViewModelService {

	@Autowired
	private NoteService noteService;

	@Autowired
	private SemesterService semesterService;

	public List<NoteViewModel> getListeDerNoteViewModelleDurchSchuler(final Schuler schuler, final Locale locale) {
		List<NoteViewModel> listOfNoteViewModels = new ArrayList<>();
		noteService.findeSchulerNoten(schuler, semesterService.findeAktuelleSemester(locale)).stream()
				.forEach(note -> listOfNoteViewModels.add(NoteUtils.noteZuNoteViewModel(note, locale)));
		return listOfNoteViewModels;
	}

	public List<NoteViewModel> getListeDerNoteViewModelleDurchSchulerUndSchulstunde(final Schuler schuler,
			final Schulstunde schulstunde, final Locale locale) {
		List<NoteViewModel> listOfNoteViewModels = new ArrayList<>();
		noteService.findeSchulerNoten(schuler, semesterService.findeAktuelleSemester(locale)).stream()
				.filter(note -> note.getSchulstunde().equals(schulstunde))
				.forEach(note -> listOfNoteViewModels.add(NoteUtils.noteZuNoteViewModel(note, locale)));
		return listOfNoteViewModels;
	}

	public List<KindViewModel> getListeDerKinderViewModelleDurchElternteil(final Elternteil elternteil,
			final Locale locale) {
		List<KindViewModel> listOfKinder = new ArrayList<>();
		elternteil.getKinder().stream().forEach(kind -> listOfKinder.add(schulerZuKinderViewModel(kind, locale)));
		return listOfKinder;
	}

	public KindViewModel schulerZuKinderViewModel(final Schuler schuler, final Locale locale) {
		return new KindViewModel(schuler.getId(), schuler.getKennzeichen(), getSchulerLetzteNote(schuler, locale),
				String.valueOf(BerechnungUtils
						.durchschnittlichHalbjaehrigeNoten(getSchulerHalbjaehrigeNoten(schuler, locale))));
	}

	private String getSchulerLetzteNote(final Schuler schuler, final Locale locale) {
		Optional<Note> note = noteService.findeSchulerLetzteNote(schuler);
		return note.isPresent() ? NoteUtils.noteZuNoteViewModel(note.get(), locale).getKennzeichen() : "n/a";
	}

	private List<Long> getSchulerHalbjaehrigeNoten(final Schuler schuler, final Locale locale) {
		List<Long> halbjaehrigeNoten = new ArrayList<>();
		schuler.getKlasse().getSchulstundeSet().stream()
				.forEach(schulstunde -> halbjaehrigeNoten.add(
						BerechnungUtils.durchschnittlichNoten(noteService.findeSchulerNotenDurchSchulstunde(schuler,
								schulstunde, semesterService.findeAktuelleSemester(locale)))));
		return halbjaehrigeNoten;
	}

}
