package com.proxiad.schultagebuch.view;

public class KindViewModel {

	private Long kindId;
	private String kindKennzeichen;
	private String letzteNoteKennzeichen;
	private String halbjaehrigeErfolg;

	public KindViewModel() {
		// nothing
	}

	public KindViewModel(Long kindId, String kindKennzeichen, String letzteNoteKennzeichen, String halbjaehrigeErfolg) {
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
		return String.join(", ", kindKennzeichen, halbjaehrigeErfolg);
	}

}
