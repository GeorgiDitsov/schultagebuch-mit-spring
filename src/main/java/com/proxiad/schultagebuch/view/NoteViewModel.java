package com.proxiad.schultagebuch.view;

public class NoteViewModel {

	private Long noteId;
	private String schulfachName;
	private String lehrerName;
	private String note;
	private String noteInsertDatum;
	private String noteUpdateDatum;

	public NoteViewModel() {
		// nothing
	}

	public NoteViewModel(Long noteId, String schulfachName, String lehrerName, String note, String noteInsertDatum,
			String noteUpdateDatum) {
		this.noteId = noteId;
		this.schulfachName = schulfachName;
		this.lehrerName = lehrerName;
		this.note = note;
		this.noteInsertDatum = noteInsertDatum;
		this.noteUpdateDatum = noteUpdateDatum;
	}

	public Long getNoteId() {
		return noteId;
	}

	public void setNoteId(Long noteId) {
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNoteInsertDatum() {
		return noteInsertDatum;
	}

	public void setNoteInsertDatum(String noteInsertDatum) {
		this.noteInsertDatum = noteInsertDatum;
	}

	public String getNoteUpdateDatum() {
		return noteUpdateDatum;
	}

	public void setNoteUpdateDatum(String noteDatum) {
		this.noteUpdateDatum = noteDatum;
	}

	public String getKennzeichen() {
		return String.join(", ", schulfachName, lehrerName, note, noteUpdateDatum);
	}

}
