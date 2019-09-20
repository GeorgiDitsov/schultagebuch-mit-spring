package com.proxiad.schultagebuch.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "klasse", uniqueConstraints = { @UniqueConstraint(columnNames = { "klasse_jahr", "klasse_buchstabe" }) })
public class Klasse {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_klasse_generator")
	@SequenceGenerator(name = "PK_klasse_generator", sequenceName = "klasse_id_seq", allocationSize = 1)
	@Column(name = "klasse_id", updatable = false)
	private int id;

	@NotNull
	@Min(1)
	@Max(12)
	@Column(name = "klasse_jahr")
	private int jahr;

	@NotBlank
	@Pattern(regexp = "^([a-z])$")
	@Column(name = "klasse_buchstabe")
	private String buchstabe;

	@Size(max = 30)
	@OneToMany(mappedBy = "klasse")
	private Set<Schuler> schulerSet;

	public Klasse() {
		// nothing
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getJahr() {
		return jahr;
	}

	public void setJahr(int jahr) {
		this.jahr = jahr;
	}

	public String getBuchstabe() {
		return buchstabe;
	}

	public void setBuchstabe(String buchstabe) {
		this.buchstabe = buchstabe;
	}

	public Set<Schuler> getSchulerSet() {
		return schulerSet;
	}

	public void setSchulerSet(Set<Schuler> schulerSet) {
		this.schulerSet = schulerSet;
	}

	public String getKennzeichen() {
		return jahr + buchstabe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Klasse other = (Klasse) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Klasse [id=" + id + ", jahr=" + jahr + ", buchstabe=" + buchstabe + "]";
	}

}
