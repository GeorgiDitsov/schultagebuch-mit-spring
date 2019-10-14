package com.proxiad.schultagebuch.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.proxiad.schultagebuch.validator.constraint.SemesterConstraint;

@Entity
@Table(name = "semester", uniqueConstraints = { @UniqueConstraint(columnNames = "semesterbeginn"),
		@UniqueConstraint(columnNames = "semesterende") })
@SemesterConstraint
public class Semester {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_semester_generator")
	@SequenceGenerator(name = "pk_semester_generator", sequenceName = "semester_id_seq", allocationSize = 1)
	@Column(name = "semester_id", updatable = false)
	private int id;

	@NotNull
	@Column(name = "semesterbeginn")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime semesterbeginn;

	@NotNull
	@Column(name = "semesterende")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime semesterende;

	public Semester() {
		// nothing
	}

	public Semester(int id, LocalDateTime semesterbeginn, LocalDateTime semesterende) {
		this.id = id;
		this.semesterbeginn = semesterbeginn;
		this.semesterende = semesterende;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public @NotNull LocalDateTime getSemesterbeginn() {
		return semesterbeginn;
	}

	public void setSemesterbeginn(LocalDateTime semesterbeginn) {
		this.semesterbeginn = semesterbeginn;
	}

	public @NotNull LocalDateTime getSemesterende() {
		return semesterende;
	}

	public void setSemesterende(LocalDateTime semesterende) {
		this.semesterende = semesterende;
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
		Semester other = (Semester) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Semester [id=" + id + ", semesterbeginn=" + semesterbeginn + ", semesterende=" + semesterende + "]";
	}

}
