package com.proxiad.schultagebuch.entity;

import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.proxiad.schultagebuch.validator.annotation.PINConstraint;
import com.proxiad.schultagebuch.validator.annotation.PersonNameConstraint;

@Entity
@Table(name = "elternteil", uniqueConstraints = { @UniqueConstraint(columnNames = "benutzer_id"),
		@UniqueConstraint(columnNames = "elternteil_pin") })
public class Elternteil {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "elterteil_generator")
	@SequenceGenerator(name = "elternteil_generator", sequenceName = "elternteil_id_seq")
	@Column(name = "elternteil_id")
	private int id;

	@PersonNameConstraint
	@Column(name = "elternteil_name")
	private String name;

	@PINConstraint
	@Column(name = "elternteil_pin")
	private String pin;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "elternteil_schuler", joinColumns = {
			@JoinColumn(name = "elternteil_id") }, inverseJoinColumns = { @JoinColumn(name = "schuler_id") })
	private Set<Schuler> kinder;

	@OneToOne(optional = true)
	@JoinColumn(name = "benutzer_id", nullable = true)
	private Benutzer benutzer;

	public Elternteil() {
		// nothing
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPin() {
		return this.pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Set<Schuler> getKinder() {
		return this.kinder;
	}

	public void setKinder(Set<Schuler> kinder) {
		this.kinder = kinder;
	}

	public Benutzer getBenutzer() {
		return this.benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

	public String getKinderKennzeichen() {
		StringBuilder kennzeichen = new StringBuilder();
		Optional.of(kinder).filter(set -> !set.isEmpty())
				.ifPresent(set -> set.forEach(schuler -> kennzeichen.append(schuler.getKennzeichen()).append("\n")));
		return kinder.isEmpty() ? "n/a" : kennzeichen.toString();
	}

	public String getKennzeichen() {
		return name + ", " + getPinKennzeichen();
	}

	public String getPinKennzeichen() {
		return pin.substring(0, 6) + "****";
	}

	public String getBenutzernameKennzeichen() {
		return Optional.ofNullable(benutzer).isPresent() ? benutzer.getBenutzerName() : "n/a";
	}

}
