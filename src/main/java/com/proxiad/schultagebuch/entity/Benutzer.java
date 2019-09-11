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
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "benutzer", uniqueConstraints = { @UniqueConstraint(columnNames = { "benutzer_name" }) })
public class Benutzer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_benutzer_generator")
	@SequenceGenerator(name = "PK_benutzer_generator", sequenceName = "benutzer_id_seq", allocationSize = 1)
	@Column(name = "benutzer_id")
	private int id;

	@NotBlank
	@Pattern(regexp = "^(([a-z0-9]+){6,20})$", message = "Falsch benutzer name")
	@Column(name = "benutzer_name", unique = true)
	private String benutzerName;

	@NotBlank
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[#$^+=!*()@%&]).{6,10}$", message = "Falsch benutzer passwort")
	@Column(name = "benutzer_pass")
	private String passwort;

	@NotNull
	@Valid
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

	public String getPasswort() {
		return this.passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public Rolle getRolle() {
		return this.rolle;
	}

	public void setRolle(Rolle rolle) {
		this.rolle = rolle;
	}

}
