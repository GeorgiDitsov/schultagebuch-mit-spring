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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.proxiad.schultagebuch.validator.constraint.BenutzerSchulerRolleConstraint;
import com.proxiad.schultagebuch.validator.constraint.PINConstraint;
import com.proxiad.schultagebuch.validator.constraint.PersonNameConstraint;

@Entity
@Table(name = "schuler", uniqueConstraints = { @UniqueConstraint(columnNames = "schuler_pin"),
		@UniqueConstraint(columnNames = "benutzer_id") })
public class Schuler {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_schuler_generator")
	@SequenceGenerator(name = "PK_schuler_generator", sequenceName = "schuler_id_seq", allocationSize = 1)
	@Column(name = "schuler_id", updatable = false)
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
	@OneToMany(mappedBy = "schuler", fetch = FetchType.EAGER)
	@OrderBy("note_datum DESC")
	private Set<Note> notenSet;

	@Size(min = 1, max = 2)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "elternteil_schuler", joinColumns = @JoinColumn(name = "schuler_id"), inverseJoinColumns = @JoinColumn(name = "elternteil_id"))
	private Set<Elternteil> eltern;

	@BenutzerSchulerRolleConstraint
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "benutzer_id", unique = true)
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

	public Set<Note> getNotenSet() {
		return notenSet;
	}

	public void setNotenSet(Set<Note> notenSet) {
		this.notenSet = notenSet;
	}

	public Set<Elternteil> getEltern() {
		return eltern;
	}

	public void setEltern(Set<Elternteil> eltern) {
		this.eltern = eltern;
	}

	public Benutzer getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

	public String getKennzeichen() {
		return name + ", " + pin + ", " + getKlasseKennzeichen();
	}

	public String getKlasseKennzeichen() {
		return Optional.ofNullable(klasse).isPresent() ? klasse.getKennzeichen() : "n/a";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((pin == null) ? 0 : pin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schuler other = (Schuler) obj;
		if (id != other.id)
			return false;
		if (pin == null) {
			if (other.pin != null)
				return false;
		} else if (!pin.equals(other.pin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Schuler [id=" + id + ", name=" + name + ", pin=" + pin + ", klasse=" + klasse + ", " + ", benutzer="
				+ benutzer + "]";
	}

}
