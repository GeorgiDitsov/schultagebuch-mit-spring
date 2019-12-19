package com.proxiad.schultagebuch.view;

import com.proxiad.schultagebuch.konstanten.StringKonstanten;

public class KindViewModell {

	private Long kindId;
	private String kindKennzeichen;
	private String letzteNoteKennzeichen;
	private String halbjaehrigeErfolg;

	public KindViewModell() {
		// nothing
	}

	public KindViewModell(Long kindId, String kindKennzeichen, String letzteNoteKennzeichen, String halbjaehrigeErfolg) {
		this.kindId = kindId;
		this.kindKennzeichen = kindKennzeichen;
		this.letzteNoteKennzeichen = letzteNoteKennzeichen;
		this.halbjaehrigeErfolg = halbjaehrigeErfolg;
	}

	public Long getKindId() {
		return kindId;
	}

	public void setKindId(Long kindId) {
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

	public String getHalbjaehrigeErfolg() {
		return halbjaehrigeErfolg;
	}

	public void setHalbjaehrigeErfolg(String halbjaehrigeErfolg) {
		this.halbjaehrigeErfolg = halbjaehrigeErfolg;
	}

	public String getKennzeichen() {
		return String.join(StringKonstanten.SEPARATOR, kindKennzeichen, halbjaehrigeErfolg);
	}

}
