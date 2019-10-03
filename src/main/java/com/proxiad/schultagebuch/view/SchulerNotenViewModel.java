package com.proxiad.schultagebuch.view;

public class SchulerNotenViewModel {

	private int schulerId;
	private String schulerKennzeichen;
	private String schulerNoten;

	public SchulerNotenViewModel() {
		// nothing
	}

	public SchulerNotenViewModel(int schulerId, String schulerKennzeichen, String schulerNoten) {
		this.setSchulerId(schulerId);
		this.setSchulerKennzeichen(schulerKennzeichen);
		this.setSchulerNoten(schulerNoten);
	}

	public int getSchulerId() {
		return schulerId;
	}

	public void setSchulerId(int schulerId) {
		this.schulerId = schulerId;
	}

	public String getSchulerKennzeichen() {
		return schulerKennzeichen;
	}

	public void setSchulerKennzeichen(String schulerKennzeichen) {
		this.schulerKennzeichen = schulerKennzeichen;
	}

	public String getSchulerNoten() {
		return schulerNoten;
	}

	public void setSchulerNoten(String schulerNoten) {
		this.schulerNoten = schulerNoten;
	}

}
