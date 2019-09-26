package com.proxiad.schultagebuch.view;

import java.time.LocalDateTime;

public class NoteView {

	private String schulfachName;
	private String lehrerName;
	private byte note;
	private LocalDateTime noteDatum;

	public NoteView() {
		// nothing
	}

	public NoteView(String schulfachName, String lehrerName, byte note, LocalDateTime noteDatum) {
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

	public LocalDateTime getNoteDatum() {
		return noteDatum;
	}

	public void setNoteDatum(LocalDateTime noteDatum) {
		this.noteDatum = noteDatum;
	}

	@Override
	public String toString() {
		return "NoteView [schulfachName=" + schulfachName + ", lehrerName=" + lehrerName + ", note=" + note
				+ ", noteDatum=" + noteDatum + "]";
	}

}
