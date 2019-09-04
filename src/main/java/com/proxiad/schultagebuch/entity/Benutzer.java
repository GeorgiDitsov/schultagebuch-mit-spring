package com.proxiad.schultagebuch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "benutzer")
public class Benutzer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_benutzer_generator")
	@SequenceGenerator(name = "PK_benutzer_generator", sequenceName = "benutzer_id_seq")
	@Column(name = "benutzer_id")
	private int id;
	@Column(name = "benutzer_name", unique = true)
	private String benutzerName;
	@Column(name = "benutzer_pass")
	private String benutzerPass;

	@ManyToOne
	@JoinColumn(name = "rolle_id", nullable = false)
	private Rolle rolle;

	public Benutzer() {
		// nothing
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBenutzerName() {
		return this.benutzerName;
	}

	public void setBenutzerName(String benutzerName) {
		this.benutzerName = benutzerName;
	}

	public String getBenutzerPass() {
		return this.benutzerPass;
	}

	public void setBenutzerPass(String benutzerPass) {
		this.benutzerPass = benutzerPass;
	}

	public Rolle getRolle() {
		return this.rolle;
	}

	public void setRolle(Rolle rolle) {
		this.rolle = rolle;
	}

}
