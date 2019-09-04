package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Schulstunde;
import com.proxiad.schultagebuch.repository.SchulstundeRepository;

@Service
@Transactional
public class SchulstundeService {

	@Autowired
	private SchulstundeRepository repo;

	public void save(Schulstunde schulstunde) {
		repo.save(schulstunde);
	}

	public List<Schulstunde> findAll() {
		return repo.findAllByOrderByKlasseIdAscIdAsc();
	}

	public Optional<Schulstunde> find(int id) {
		return repo.findById(id);
	}

	public void delete(Schulstunde schulstunde) {
		repo.delete(schulstunde);
	}
}
