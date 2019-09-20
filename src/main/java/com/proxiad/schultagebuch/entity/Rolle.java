package com.proxiad.schultagebuch.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.proxiad.schultagebuch.util.RolleTyp;

@Entity
@Table(name = "rolle", uniqueConstraints = @UniqueConstraint(columnNames = "rolle_name"))
public class Rolle {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_rolle_generator")
	@SequenceGenerator(name = "PK_rolle_generator", sequenceName = "rolle_id_seq", allocationSize = 1)
	@Column(name = "rolle_id", updatable = false)
	private int id;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "rolle_name", unique = true)
	private RolleTyp name;

	@OneToMany(mappedBy = "rolle")
	private Set<Benutzer> benutzerSet;

	public Rolle() {
		// nothing
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RolleTyp getName() {
		return name;
	}

	public void setName(RolleTyp name) {
		this.name = name;
	}

	public Set<Benutzer> getBenutzerSet() {
		return benutzerSet;
	}

	public void setBenutzerSet(Set<Benutzer> benutzerSet) {
		this.benutzerSet = benutzerSet;
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
		Rolle other = (Rolle) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Rolle [id=" + id + ", name=" + name + "]";
	}

}
