package com.proxiad.schultagebuch.entity;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.proxiad.schultagebuch.validator.constraint.PINConstraint;
import com.proxiad.schultagebuch.validator.constraint.PersonNameConstraint;

@Entity
@Table(name = "elternteil", uniqueConstraints = { @UniqueConstraint(columnNames = { "benutzer_id" }),
		@UniqueConstraint(columnNames = { "elternteil_pin" }) })
public class Elternteil {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_elternteil_generator")
	@SequenceGenerator(name = "PK_elternteil_generator", sequenceName = "elternteil_id_seq", allocationSize = 1)
	@Column(name = "elternteil_id")
	private int id;

	@PersonNameConstraint
	@Column(name = "elternteil_name")
	private String name;

	@PINConstraint
	@Column(name = "elternteil_pin")
	private String pin;

	// @Size(min = 1)
	@ManyToMany(mappedBy = "eltern", fetch = FetchType.EAGER)
	private Set<Schuler> kinder;

	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "benutzer_id", unique = true, nullable = true)
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

}
