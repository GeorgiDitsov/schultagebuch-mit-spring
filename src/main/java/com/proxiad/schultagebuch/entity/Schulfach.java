package com.proxiad.schultagebuch.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "schulfach", uniqueConstraints = @UniqueConstraint(columnNames = "schulfach_name"))
public class Schulfach {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_schulfach_generator")
	@SequenceGenerator(name = "PK_schulfach_generator", sequenceName = "schulfach_id_seq", allocationSize = 1)
	@Column(name = "schulfach_id", updatable = false)
	private int id;

	@NotBlank
	@Pattern(regexp = "^([A-Z][a-z]+)$", message = "{invalid.subject.name}")
	@Column(name = "schulfach_name")
	private String name;

	@Size(max = 5)
	@ManyToMany(mappedBy = "schulfachSet", fetch = FetchType.EAGER)
	private Set<Lehrer> lehrerSet;

	public Schulfach() {
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

	public Set<Lehrer> getLehrerSet() {
		return lehrerSet;
	}

	public void setLehrerSet(Set<Lehrer> lehrerSet) {
		this.lehrerSet = lehrerSet;
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
		Schulfach other = (Schulfach) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Schulfach [id=" + id + ", name=" + name + ", lehrerSet=" + lehrerSet + "]";
	}
}
