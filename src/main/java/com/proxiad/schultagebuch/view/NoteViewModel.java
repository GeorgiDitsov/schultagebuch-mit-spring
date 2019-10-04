package com.proxiad.schultagebuch.view;

public class NoteViewModel {

	private String schulfachName;
	private String lehrerName;
	private byte note;
	private String noteDatum;

	public NoteViewModel() {
		// nothing
	}

	public NoteViewModel(String schulfachName, String lehrerName, byte note, String noteDatum) {
		this.schulfachName = schulfachName;
		this.lehrerName = lehrerName;
		this.note = note;
		this.noteDatum = noteDatum;
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

	@Override
	public String toString() {
		return schulfachName + ", " + lehrerName + ", " + note + ", " + noteDatum;
	}

}
