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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "rolle", uniqueConstraints = { @UniqueConstraint(columnNames = "rolle_name") })
public class Rolle {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_rolle_generator")
	@SequenceGenerator(name = "PK_rolle_generator", sequenceName = "rolle_id_seq", allocationSize = 1)
	@Column(name = "rolle_id")
	private int id;

	@NotBlank
	@Pattern(regexp = "^(ROLE_[A-Z]+)$", message = "Falsch rolle name")
	@Column(name = "rolle_name", unique = true)
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKennzeichen() {
		return name.substring(5, 6) + name.substring(6).toLowerCase();
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
}
