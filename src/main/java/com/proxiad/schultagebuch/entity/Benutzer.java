package com.proxiad.schultagebuch.entity;

import java.util.Objects;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "benutzer", uniqueConstraints = @UniqueConstraint(columnNames = "benutzer_name"))
public class Benutzer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK_benutzer_generator")
	@SequenceGenerator(name = "PK_benutzer_generator", sequenceName = "benutzer_id_seq", allocationSize = 1)
	@Column(name = "benutzer_id", updatable = false)
	private Long id;

	@NotBlank
	@Pattern(regexp = "^([A-Za-z0-9]{5,20})$", message = "{invalid.user.name}")
	@Column(name = "benutzer_name", unique = true)
	private String benutzerName;

	@NotBlank
	@Pattern(regexp = "^(?=.*[a-zA-Z0-9])[a-zA-Z0-9]{5,10}$", message = "{invalid.user.password}")
	@Column(name = "benutzer_pass")
	@ColumnTransformer(write = "crypt(?, gen_salt('bf'))")
	private String passwort;

	@NotNull
	@Valid
	@ManyToOne
	@JoinColumn(name = "rolle_id", nullable = false)
	private Rolle rolle;

	public Benutzer() {
		// nothing
	}

	public Benutzer(Long id, String benutzerName, String passwort, Rolle rolle) {
		this.id = id;
		this.benutzerName = benutzerName;
		this.passwort = passwort;
		this.rolle = rolle;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBenutzerName() {
		return this.benutzerName;
	}

	public void setBenutzerName(String benutzerName) {
		this.benutzerName = benutzerName;
	}

	public String getPasswort() {
		return this.passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public Rolle getRolle() {
		return this.rolle;
	}

	public void setRolle(Rolle rolle) {
		this.rolle = rolle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Objects.isNull(benutzerName)) ? 0 : benutzerName.hashCode());
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
		Benutzer other = (Benutzer) obj;
		if (Objects.isNull(benutzerName)) {
			if (Objects.nonNull(other.benutzerName))
				return false;
		} else if (!benutzerName.equals(other.benutzerName))
			return false;
		if (Objects.isNull(id)) {
			if (Objects.nonNull(other.id))
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Benutzer [id=" + id + ", benutzerName=" + benutzerName + ", passwort=" + passwort + ", rolle=" + rolle
				+ "]";
	}

}
