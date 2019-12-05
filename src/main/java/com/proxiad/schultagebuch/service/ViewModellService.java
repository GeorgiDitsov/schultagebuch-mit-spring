package com.proxiad.schultagebuch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.konstanten.StringKonstanten;
import com.proxiad.schultagebuch.util.BerechnungUtils;
import com.proxiad.schultagebuch.util.DatumUtils;
import com.proxiad.schultagebuch.view.KindViewModell;
import com.proxiad.schultagebuch.view.NoteViewModell;
import com.proxiad.schultagebuch.view.SemesterViewModell;

@Service
@Transactional
public class ViewModellService {

	@Autowired
	private NoteService noteService;

	@Autowired
	private SemesterService semesterService;

	public List<NoteViewModell> getListeDerNoteViewModelleDurchSchuler(final Schuler schuler, final Locale locale) {
		List<NoteViewModell> listOfNoteViewModelle = new ArrayList<>();
		noteService.findeSchulerNoten(schuler, semesterService.findeAktuelleSemester()).stream()
				.forEach(note -> listOfNoteViewModelle.add(note.toNoteViewModell(locale)));
		return listOfNoteViewModelle;
	}

	public List<NoteViewModell> getListeDerNoteViewModelleDurchSchulerUndSchulstunde(final Schuler schuler,
			final Schulstunde schulstunde, final Locale locale) {
		List<NoteViewModell> listOfNoteViewModelle = new ArrayList<>();
		noteService.findeSchulerNoten(schuler, semesterService.findeAktuelleSemester()).stream()
				.filter(note -> note.getSchulstunde().equals(schulstunde))
				.forEach(note -> listOfNoteViewModelle.add(note.toNoteViewModell(locale)));
		return listOfNoteViewModelle;
	}

	public List<KindViewModell> getListeDerKinderViewModelleDurchElternteil(final Elternteil elternteil,
			final Locale locale) {
		List<KindViewModell> listOfKinder = new ArrayList<>();
		elternteil.getKinder().stream().forEach(kind -> listOfKinder.add(schulerZuKinderViewModel(kind, locale)));
		return listOfKinder;
	}

	public List<SemesterViewModell> getListerDerSemesterViewModelle(final Locale locale) {
		List<SemesterViewModell> listOfSemesterViewModelle = new ArrayList<>();
		semesterService.findeAlle().stream()
				.forEach(semester -> listOfSemesterViewModelle.add(new SemesterViewModell(semester.getId(),
						DatumUtils.localDateTimeZuString(semester.getSemesterbeginn(), locale),
						DatumUtils.localDateTimeZuString(semester.getSemesterende(), locale))));
		return listOfSemesterViewModelle;
	}

	public KindViewModell schulerZuKinderViewModel(final Schuler schuler, final Locale locale) {
		return new KindViewModell(schuler.getId(), schuler.getKennzeichen(), getSchulerLetzteNote(schuler, locale),
				String.valueOf(
						BerechnungUtils.durchschnittlichHalbjaehrigeNoten(getSchulerHalbjaehrigeNoten(schuler))));
	}

	private String getSchulerLetzteNote(final Schuler schuler, final Locale locale) {
		return noteService.findeSchulerLetzteNote(schuler).map(note -> note.toNoteViewModell(locale).getKennzeichen())
				.orElse(StringKonstanten.OBJEKT_NICHT_VERFUEGBAR);
	}

	private List<Long> getSchulerHalbjaehrigeNoten(final Schuler schuler) {
		List<Long> halbjaehrigeNoten = new ArrayList<>();
		Optional.ofNullable(schuler.getKlasse()).ifPresent(klasse -> klasse.getSchulstundeSet().stream()
				.forEach(schulstunde -> halbjaehrigeNoten.add(
						BerechnungUtils.durchschnittlichNoten(noteService.findeSchulerNotenDurchSchulstunde(schuler,
								schulstunde, semesterService.findeAktuelleSemester())))));
		return halbjaehrigeNoten;
	}

}
