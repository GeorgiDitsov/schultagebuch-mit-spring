package com.proxiad.schultagebuch.entity;

import java.util.Objects;
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

import com.proxiad.schultagebuch.konstanten.StringKonstanten;
import com.proxiad.schultagebuch.util.KennzeichenUtils;
import com.proxiad.schultagebuch.validator.constraint.SchulstundeConstraint;

@Entity
@Table(name = "schulstunde", uniqueConstraints = @UniqueConstraint(columnNames = { "klasse_id", "schulfach_id" }))
@SchulstundeConstraint
public class Schulstunde {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_schulstunde_generator")
	@SequenceGenerator(name = "PK_schulstunde_generator", sequenceName = "schulstunde_id_seq", allocationSize = 1)
	@Column(name = "schulstunde_id", updatable = false)
	private Long id;

	@NotNull
	@Valid
	@ManyToOne
	@JoinColumn(name = "klasse_id", nullable = false)
	private Klasse klasse;

	@NotNull
	@Valid
	@ManyToOne
	@JoinColumn(name = "schulfach_id", nullable = false)
	private Schulfach schulfach;

	@Valid
	@ManyToOne(optional = true)
	@JoinColumn(name = "lehrer_id", nullable = true)
	private Lehrer lehrer;

	public Schulstunde() {
		// nothing
	}

	public Schulstunde(Long id, Klasse klasse, Schulfach schulfach, Lehrer lehrer) {
		this.id = id;
		this.klasse = klasse;
		this.schulfach = schulfach;
		this.lehrer = lehrer;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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
		return Optional.ofNullable(lehrer).isPresent() ? KennzeichenUtils.menschKennzeichen(lehrer)
				: StringKonstanten.OBJEKT_NICHT_VERFUEGBAR;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Objects.isNull(id)) ? 0 : id.hashCode());
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
		Schulstunde other = (Schulstunde) obj;
		if (Objects.isNull(id)) {
			if (Objects.nonNull(other.id))
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Schulstunde [id=" + id + ", klasse=" + klasse + ", schulfach=" + schulfach + ", lehrer=" + lehrer + "]";
	}
}
