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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

import com.proxiad.schultagebuch.validator.constraint.BenutzerLehrerRolleContraint;
import com.proxiad.schultagebuch.validator.constraint.PINConstraint;
import com.proxiad.schultagebuch.validator.constraint.PersonNameConstraint;

@Entity
@Table(name = "lehrer", uniqueConstraints = { @UniqueConstraint(columnNames = "benutzer_id"),
		@UniqueConstraint(columnNames = "lehrer_pin") })
public class Lehrer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_lehrer_generator")
	@SequenceGenerator(name = "PK_lehrer_generator", sequenceName = "lehrer_id_seq", allocationSize = 1)
	@Column(name = "lehrer_id", updatable = false)
	private Long id;

	@PersonNameConstraint
	@Column(name = "lehrer_name")
	private String name;

	@PINConstraint
	@Column(name = "lehrer_pin", unique = true)
	private String pin;

	@Valid
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "lehrer_schulfach", joinColumns = { @JoinColumn(name = "lehrer_id") }, inverseJoinColumns = {
			@JoinColumn(name = "schulfach_id") })
	private Set<Schulfach> schulfachSet;

	@BenutzerLehrerRolleContraint
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "benutzer_id", unique = true)
	private Benutzer benutzer;

	public Lehrer() {
		// nothing
	}

	public Lehrer(Long id, String name, String pin, Benutzer benutzer) {
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

	public String getKennzeichen() {
		return String.join(", ", name, pin);
	}

	public String getSchulfaecherKennzeichen() {
		StringBuilder kennzeichen = new StringBuilder();
		Optional.of(schulfachSet).filter(set -> !set.isEmpty())
				.ifPresent(set -> set.forEach(schulfach -> kennzeichen.append(schulfach.getName()).append("\n")));
		return schulfachSet.isEmpty() ? "n/a" : kennzeichen.toString();
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
		Lehrer other = (Lehrer) obj;
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
		return "Lehrer [id=" + id + ", name=" + name + ", pin=" + pin + ", " + ", benutzer=" + benutzer + "]";
	}

}
