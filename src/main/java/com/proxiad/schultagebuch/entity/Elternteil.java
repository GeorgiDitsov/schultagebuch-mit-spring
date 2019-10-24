package com.proxiad.schultagebuch.entity;

import java.util.Objects;
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
import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.proxiad.schultagebuch.validator.constraint.BenutzerElternteilRolleContraint;
import com.proxiad.schultagebuch.validator.constraint.PINConstraint;
import com.proxiad.schultagebuch.validator.constraint.PersonNameConstraint;

@Entity
@Table(name = "elternteil", uniqueConstraints = { @UniqueConstraint(columnNames = "benutzer_id"),
		@UniqueConstraint(columnNames = "elternteil_pin") })
public class Elternteil {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_elternteil_generator")
	@SequenceGenerator(name = "PK_elternteil_generator", sequenceName = "elternteil_id_seq", allocationSize = 1)
	@Column(name = "elternteil_id", updatable = false)
	private Long id;

	@PersonNameConstraint
	@Column(name = "elternteil_name")
	private String name;

	@PINConstraint
	@Column(name = "elternteil_pin", unique = true)
	private String pin;

	@Valid
	@Size(min = 1)
	@ManyToMany(mappedBy = "eltern", fetch = FetchType.EAGER)
	private Set<Schuler> kinder;

	@BenutzerElternteilRolleContraint
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "benutzer_id", unique = true)
	private Benutzer benutzer;

	public Elternteil() {
		// nothing
	}

	public Elternteil(Long id, String name, String pin, Benutzer benutzer) {
		this.id = id;
		this.name = name;
		this.pin = pin;
		this.benutzer = benutzer;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	public String getKennzeichen() {
		return name + ", " + pin;
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
		Elternteil other = (Elternteil) obj;
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
		return "Elternteil [id=" + id + ", name=" + name + ", pin=" + pin + ", " + ", benutzer=" + benutzer + "]";
	}

}
