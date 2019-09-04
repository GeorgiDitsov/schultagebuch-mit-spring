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
	@Column(name = "klasse_id")
	private int id;

	@NotNull(message = "Das jahr ist obligatorisch!")
	@Min(1)
	@Max(12)
	@Column(name = "klasse_jahr")
	private int jahr;

	@NotBlank(message = "Der buchstabe ist obligatorisch!")
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
}
