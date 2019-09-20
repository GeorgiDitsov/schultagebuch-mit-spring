package com.proxiad.schultagebuch.util;

public enum RolleTyp {

	ROLLE_ADMIN("ROLE_ADMIN"), ROLLE_SCHULER("ROLE_SCHULER"), ROLLE_LEHRER("ROLE_LEHRER"),
	ROLLE_ELTERNTEIL("ROLE_ELTERNTEIL");

	private final String name;

	private RolleTyp(String name) {
		this.name = name;
	}

	public String getKennzeichen() {
		return name.substring(5, 6) + name.substring(6).toLowerCase();
	}

	@Override
	public String toString() {
		return name;
	}

}
