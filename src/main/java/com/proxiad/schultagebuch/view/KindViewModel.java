package com.proxiad.schultagebuch.view;

public class KindViewModel {

	private int kindId;
	private String kindKennzeichen;
	private String letzteNoteKennzeichen;

	public KindViewModel() {
		// nothing
	}

	public KindViewModel(int kindId, String kindKennzeichen, String letzteNoteKennzeichen) {
		this.kindId = kindId;
		this.kindKennzeichen = kindKennzeichen;
		this.letzteNoteKennzeichen = letzteNoteKennzeichen;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}

	public String getKindKennzeichen() {
		return kindKennzeichen;
	}

	public void setKindKennzeichen(String kindName) {
		this.kindKennzeichen = kindName;
	}

	public String getLetzteNoteKennzeichen() {
		return letzteNoteKennzeichen;
	}

	public void setLetzteNoteKennzeichen(String letzteNoteKennzeichen) {
		this.letzteNoteKennzeichen = letzteNoteKennzeichen;
	}

}
