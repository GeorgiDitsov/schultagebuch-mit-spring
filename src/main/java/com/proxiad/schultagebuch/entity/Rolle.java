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

@Entity
@Table(name = "rolle")
public class Rolle {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rolle_generator")
	@SequenceGenerator(name = "rolle_generator", sequenceName = "rolle_id_seq")
	@Column(name = "rolle_id")
	private int id;
	@Column(name = "rolle_name")
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

}
