package com.proxiad.schultagebuch.util;

import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulstunde;

public final class NoteUtils {

	private NoteUtils() {
		// nothing
	}

	public static Note erstellenNeueNoteMitSchulerUndSchulstunde(final Schuler schuler, final Schulstunde schulstunde) {
		Note note = new Note();
		note.setSchuler(schuler);
		note.setSchulstunde(schulstunde);
		return note;
	}

}
