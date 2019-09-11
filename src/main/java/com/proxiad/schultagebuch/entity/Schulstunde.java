package com.proxiad.schultagebuch.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.proxiad.schultagebuch.validator.annotation.SchulstundeConstraint;

@Entity
@SchulstundeConstraint
@Table(name = "schulstunde", uniqueConstraints = { @UniqueConstraint(columnNames = { "klasse_id", "schulfach_id" }) })
public class Schulstunde {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_schulstunde_generator")
	@SequenceGenerator(name = "PK_schulstunde_generator", sequenceName = "schulstunde_id_seq", allocationSize = 1)
	@Column(name = "schulstunde_id")
	private int id;

	@NotNull
	@Valid
	@ManyToOne
	@JoinColumn(name = "klasse_id")
	private Klasse klasse;

	@NotNull
	@Valid
	@ManyToOne
	@JoinColumn(name = "schulfach_id")
	private Schulfach schulfach;

	@Valid
	@ManyToOne(optional = true)
	@JoinColumn(name = "lehrer_id", nullable = true)
	private Lehrer lehrer;

	public Schulstunde() {
		// nothing
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Klasse getKlasse() {
		return this.klasse;
	}

	public void setKlasse(Klasse klasse) {
		this.klasse = klasse;
	}

	public Schulfach getSchulfach() {
		return this.schulfach;
	}

	public void setSchulfach(Schulfach schulfach) {
		this.schulfach = schulfach;
	}

	public Lehrer getLehrer() {
		return this.lehrer;
	}

	public void setLehrer(Lehrer lehrer) {
		this.lehrer = lehrer;
	}

	public String getLehrerKennzeichen() {
		return Optional.ofNullable(lehrer).isPresent() ? lehrer.getKennzeichen() : "n/a";
	}

}
