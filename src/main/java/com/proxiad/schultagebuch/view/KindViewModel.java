package com.proxiad.schultagebuch.view;

public class KindViewModel {

	private int kindId;
	private String kindKennzeichen;
	private String letzteNoteKennzeichen;
	private double halbjaehrigeErfolg;

	public KindViewModel() {
		// nothing
	}

	public KindViewModel(int kindId, String kindKennzeichen, String letzteNoteKennzeichen, double halbjaehrigeErfolg) {
		this.kindId = kindId;
		this.kindKennzeichen = kindKennzeichen;
		this.letzteNoteKennzeichen = letzteNoteKennzeichen;
		this.halbjaehrigeErfolg = halbjaehrigeErfolg;
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

	public double getHalbjaehrigeErfolg() {
		return halbjaehrigeErfolg;
	}

	public void setHalbjaehrigeErfolg(double halbjaehrigeErfolg) {
		this.halbjaehrigeErfolg = halbjaehrigeErfolg;
	}

	public String getKennzeichen() {
		return kindKennzeichen + ", " + halbjaehrigeErfolg;
	}

}
