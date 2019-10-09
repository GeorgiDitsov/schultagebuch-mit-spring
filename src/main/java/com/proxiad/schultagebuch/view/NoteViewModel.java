package com.proxiad.schultagebuch.view;

public class NoteViewModel {

	private int noteId;
	private String schulfachName;
	private String lehrerName;
	private byte note;
	private String noteDatum;

	public NoteViewModel() {
		// nothing
	}

	public NoteViewModel(int noteId, String schulfachName, String lehrerName, byte note, String noteDatum) {
		this.noteId = noteId;
		this.schulfachName = schulfachName;
		this.lehrerName = lehrerName;
		this.note = note;
		this.noteDatum = noteDatum;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getSchulfachName() {
		return schulfachName;
	}

	public void setSchulfachName(String schulfachName) {
		this.schulfachName = schulfachName;
	}

	public String getLehrerName() {
		return lehrerName;
	}

	public void setLehrerName(String lehrerName) {
		this.lehrerName = lehrerName;
	}

	public byte getNote() {
		return note;
	}

	public void setNote(byte note) {
		this.note = note;
	}

	public String getNoteDatum() {
		return noteDatum;
	}

	public void setNoteDatum(String noteDatum) {
		this.noteDatum = noteDatum;
	}

	public String getKennzeichen() {
		return schulfachName + ", " + lehrerName + ", " + note + ", " + noteDatum;
	}

}
