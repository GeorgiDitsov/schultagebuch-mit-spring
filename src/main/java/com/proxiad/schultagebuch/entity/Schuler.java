package com.proxiad.schultagebuch.entity;

import java.util.Objects;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.proxiad.schultagebuch.konstanten.StringKonstanten;
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
	private Long id;

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

	@Size(min = 1, max = 2)
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinTable(name = "elternteil_schuler", joinColumns = @JoinColumn(name = "schuler_id"), inverseJoinColumns = @JoinColumn(name = "elternteil_id"))
	private Set<Elternteil> eltern;

	@BenutzerSchulerRolleConstraint
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "benutzer_id", unique = true)
	private Benutzer benutzer;

	public Schuler() {
		// nothing
	}

	public Schuler(Long id, String name, String pin, Klasse klasse, Benutzer benutzer) {
		this.id = id;
		this.name = name;
		this.pin = pin;
		this.klasse = klasse;
		this.benutzer = benutzer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		return String.join(StringKonstanten.SEPARATOR, name, pin, getKlasseKennzeichen());
	}

	public String getKlasseKennzeichen() {
		return Optional.ofNullable(klasse).isPresent() ? klasse.getKennzeichen()
				: StringKonstanten.OBJEKT_NICHT_VERFUEGBAR;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Objects.isNull(id)) ? 0 : id.hashCode());
		result = prime * result + ((Objects.isNull(pin)) ? 0 : pin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (Objects.isNull(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schuler other = (Schuler) obj;
		if (Objects.isNull(id)) {
			if (Objects.nonNull(other.id))
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Objects.isNull(pin)) {
			if (Objects.nonNull(other.pin))
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
