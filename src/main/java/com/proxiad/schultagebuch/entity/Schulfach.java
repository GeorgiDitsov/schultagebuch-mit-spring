package com.proxiad.schultagebuch.entity;

import java.util.Objects;
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
	private Long id;

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

	public Schulfach(Long id, String name) {
		this.id = id;
		this.name = name;
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
		result = prime * result + ((Objects.isNull(id)) ? 0 : id.hashCode());
		result = prime * result + ((Objects.isNull(name)) ? 0 : name.hashCode());
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
		Schulfach other = (Schulfach) obj;
		if (Objects.isNull(id)) {
			if (Objects.nonNull(other.id))
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Objects.isNull(name)) {
			if (Objects.nonNull(other.name))
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Schulfach [id=" + id + ", name=" + name + ", lehrerSet=" + lehrerSet + "]";
	}
}
