package com.proxiad.schultagebuch.entity;

import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.proxiad.schultagebuch.validator.annotation.PINConstraint;
import com.proxiad.schultagebuch.validator.annotation.PersonNameConstraint;

@Entity
@Table(name = "schuler", uniqueConstraints = { @UniqueConstraint(columnNames = { "schuler_pin" }),
		@UniqueConstraint(columnNames = { "benutzer_id" }) })
public class Schuler {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_schuler_generator")
	@SequenceGenerator(name = "PK_schuler_generator", sequenceName = "schuler_id_seq", allocationSize = 1)
	@Column(name = "schuler_id")
	private int id;

	@PersonNameConstraint
	@Column(name = "schuler_name")
	private String name;

	@PINConstraint
	@Column(name = "schuler_pin", unique = true)
	private String pin;

	@Valid
	@ManyToOne(optional = true)
	@JoinColumn(name = "klasse_id", nullable = true)
	private Klasse klasse;

	@Valid
	@Size(min = 1, max = 2)
	@ManyToMany(mappedBy = "kinder", fetch = FetchType.EAGER)
	private Set<Elternteil> eltern;

	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "benutzer_id", unique = true, nullable = true)
	private Benutzer benutzer;

	public Schuler() {
		// nothing
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Klasse getKlasse() {
		return klasse;
	}

	public void setKlasse(Klasse klasse) {
		this.klasse = klasse;
	}

	public Benutzer getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

	public Set<Elternteil> getEltern() {
		return eltern;
	}

	public void setEltern(Set<Elternteil> eltern) {
		this.eltern = eltern;
	}

	public String getKlasseKennzeichen() {
		return Optional.ofNullable(klasse).isPresent() ? klasse.getKennzeichen() : "n/a";
	}

	public String getBenutzernameKennzeichen() {
		return Optional.ofNullable(benutzer).isPresent() ? benutzer.getBenutzerName() : "n/a";
	}

	public String getElternKennzeichen() {
		StringBuilder kennzeichen = new StringBuilder();
		Optional.of(eltern).filter(set -> !set.isEmpty()).ifPresent(
				set -> set.forEach(elternteil -> kennzeichen.append(elternteil.getKennzeichen()).append("\n")));
		return eltern.isEmpty() ? "n/a" : kennzeichen.toString();
	}

	public String getKennzeichen() {
		return name + ", " + getPinKennzeichen();
	}

	public String getPinKennzeichen() {
		return pin.substring(0, 6) + "****";
	}

}
