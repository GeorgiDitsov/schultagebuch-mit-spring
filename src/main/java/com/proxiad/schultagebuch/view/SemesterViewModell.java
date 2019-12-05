package com.proxiad.schultagebuch.view;

public class SemesterViewModell {

	private int semesterId;
	private String semesterbeginn;
	private String semesterende;

	public SemesterViewModell() {
		// nothing
	}

	public SemesterViewModell(int semesterId, String semesterbeginn, String semesterende) {
		this.semesterId = semesterId;
		this.semesterbeginn = semesterbeginn;
		this.semesterende = semesterende;
	}

	public int getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}

	public String getSemesterbeginn() {
		return semesterbeginn;
	}

	public void setSemesterbeginn(String semesterbeginn) {
		this.semesterbeginn = semesterbeginn;
	}

	public String getSemesterende() {
		return semesterende;
	}

	public void setSemesterende(String semesterende) {
		this.semesterende = semesterende;
	}
}
