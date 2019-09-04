package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.repository.KlasseRepository;

@Service
@Transactional
public class KlasseService {

	@Autowired
	private KlasseRepository repo;

	public void save(Klasse klasse) {
		repo.save(klasse);
	}

	public List<Klasse> findAll() {
		return repo.findAllByOrderByIdAsc();
	}

	public Optional<Klasse> find(int id) {
		return repo.findById(id);
	}

	public void delete(Klasse klasse) {
		repo.delete(klasse);
	}
}
