package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Rolle;
import com.proxiad.schultagebuch.repository.RolleRepository;
import com.proxiad.schultagebuch.util.RolleTyp;

@Service
@Transactional
public class RolleService {

	@Autowired
	private RolleRepository repo;

	@Autowired
	private MessageSource messageSource;

	public List<Rolle> findAll() {
		return repo.findAllByOrderByIdAsc();
	}

	public Rolle find(RolleTyp rolleName, final Locale locale) {
		return repo.findByName(rolleName).orElseThrow(
				() -> new IllegalArgumentException(messageSource.getMessage("invalid.role", null, locale)));
	}

}
