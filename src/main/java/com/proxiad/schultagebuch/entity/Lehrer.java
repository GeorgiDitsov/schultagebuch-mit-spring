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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.proxiad.schultagebuch.validator.annotation.PINConstraint;
import com.proxiad.schultagebuch.validator.annotation.PersonNameConstraint;

@Entity
@Table(name = "lehrer", uniqueConstraints = { @UniqueConstraint(columnNames = "benutzer_id"),
		@UniqueConstraint(columnNames = "lehrer_pin") })
public class Lehrer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_lehrer_generator")
	@SequenceGenerator(name = "PK_lehrer_generator", sequenceName = "lehrer_id_seq", allocationSize = 1)
	@Column(name = "lehrer_id")
	private int id;

	@PersonNameConstraint
	@Column(name = "lehrer_name")
	private String name;

	@PINConstraint
	@Column(name = "lehrer_pin")
	private String pin;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "lehrer_schulfach", joinColumns = { @JoinColumn(name = "lehrer_id") }, inverseJoinColumns = {
			@JoinColumn(name = "schulfach_id") })
	private Set<Schulfach> schulfachSet;

	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "benutzer_id", unique = true, nullable = true)
	private Benutzer benutzer;

	public Lehrer() {
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

	public Set<Schulfach> getSchulfachSet() {
		return this.schulfachSet;
	}

	public void setSchulfachSet(Set<Schulfach> schulfachSet) {
		this.schulfachSet = schulfachSet;
	}

	public Benutzer getBenutzer() {
		return this.benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

	public String getSchulfaecherKennzeichen() {
		StringBuilder kennzeichen = new StringBuilder();
		Optional.of(schulfachSet).filter(set -> !set.isEmpty())
				.ifPresent(set -> set.forEach(schulfach -> kennzeichen.append(schulfach.getName()).append("\n")));
		return schulfachSet.isEmpty() ? "n/a" : kennzeichen.toString();
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
