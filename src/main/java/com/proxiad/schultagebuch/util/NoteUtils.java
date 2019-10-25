package com.proxiad.schultagebuch.util;

import java.util.Locale;

import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.view.NoteViewModel;

public final class NoteUtils {

	private NoteUtils() {
		// nothing
	}

	public static Note getNeueNoteFuerSchulerUndSchulstunde(final Schuler schuler, final Schulstunde schulstunde) {
		Note note = new Note();
		note.setSchuler(schuler);
		note.setSchulstunde(schulstunde);
		return note;
	}

	public static NoteViewModel noteZuNoteViewModel(final Note note, final Locale locale) {
		return new NoteViewModel(note.getId(), note.getSchulstunde().getSchulfach().getName(),
				note.getSchulstunde().getLehrer().getName(), note.getWert(),
				DatumUtils.localDateTimeZuString(note.getNoteInsertDatum(), locale),
				DatumUtils.localDateTimeZuString(note.getNoteUpdateDatum(), locale));
	}

}
