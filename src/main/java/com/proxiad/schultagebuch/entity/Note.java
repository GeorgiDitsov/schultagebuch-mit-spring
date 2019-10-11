package com.proxiad.schultagebuch.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.UpdateTimestamp;

import com.proxiad.schultagebuch.validator.constraint.NoteConstraint;

@Entity
@Table(name = "note")
@NoteConstraint
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_note_generator")
	@SequenceGenerator(name = "PK_note_generator", sequenceName = "note_id_seq", allocationSize = 1)
	@Column(name = "note_id", updatable = false)
	private int id;

	@NotNull
	@Min(2)
	@Max(6)
	@Column(name = "note_wert", nullable = false)
	private byte wert;

	@UpdateTimestamp
	@Column(name = "note_datum", nullable = false)
	private LocalDateTime datum;

	@Valid
	@ManyToOne
	@JoinColumn(name = "schuler_id", nullable = false)
	private Schuler schuler;

	@Valid
	@ManyToOne
	@JoinColumn(name = "schulstunde_id", nullable = false)
	private Schulstunde schulstunde;

	public Note() {
		// nothing
	}

	public Note(int id, byte wert, LocalDateTime datum, Schuler schuler, Schulstunde schulstunde) {
		this.id = id;
		this.wert = wert;
		this.datum = datum;
		this.schuler = schuler;
		this.schulstunde = schulstunde;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getWert() {
		return wert;
	}

	public void setWert(byte wert) {
		this.wert = wert;
	}

	public LocalDateTime getDatum() {
		return datum;
	}

	public void setDatum(LocalDateTime datum) {
		this.datum = datum;
	}

	public Schuler getSchuler() {
		return schuler;
	}

	public void setSchuler(Schuler schuler) {
		this.schuler = schuler;
	}

	public Schulstunde getSchulstunde() {
		return schulstunde;
	}

	public void setSchulstunde(Schulstunde schulstunde) {
		this.schulstunde = schulstunde;
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
		Note other = (Note) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", wert=" + wert + ", datum=" + datum + ", schuler=" + schuler + ", schulstunde="
				+ schulstunde + "]";
	}

}
