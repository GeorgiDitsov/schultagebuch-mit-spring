package com.proxiad.schultagebuch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.entity.Rolle;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.RolleRepository;
import com.proxiad.schultagebuch.util.RolleTyp;

@Service
@Transactional
public class RolleService {

	private static final RolleTyp FALSCH_ROLLE = null;

	@Autowired
	private RolleRepository repo;

	public List<Rolle> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public Rolle findenDurchPerson(final Object person) {
		return repo
				.findByName(person instanceof Schuler ? RolleTyp.ROLLE_SCHULER
						: person instanceof Lehrer ? RolleTyp.ROLLE_LEHRER
								: person instanceof Elternteil ? RolleTyp.ROLLE_ELTERNTEIL : FALSCH_ROLLE)
				.orElseThrow(() -> new EntityNichtGefundenException("role.not.found", new Object[] { null }));
	}

}
