package com.proxiad.schultagebuch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Rolle;
import com.proxiad.schultagebuch.exception.EntityNichtGefundenException;
import com.proxiad.schultagebuch.repository.RolleRepository;
import com.proxiad.schultagebuch.util.RolleTyp;

@Service
@Transactional
public class RolleService {

	@Autowired
	private RolleRepository repo;

	public List<Rolle> findeAlle() {
		return repo.findAllByOrderByIdAsc();
	}

	public Rolle finden(final RolleTyp rolleTyp) {
		return repo.findByName(rolleTyp)
				.orElseThrow(() -> new EntityNichtGefundenException("role.not.found", new Object[] { rolleTyp }));
	}

}
