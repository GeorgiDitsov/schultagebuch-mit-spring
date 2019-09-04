package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Schulfach;
import com.proxiad.schultagebuch.repository.SchulfachRepository;

@Service
@Transactional
public class SchulfachService {

	@Autowired
	private SchulfachRepository repo;

	public void save(Schulfach schulfach) {
		repo.save(schulfach);
	}

	public List<Schulfach> findAll() {
		return repo.findAllByOrderByIdAsc();
	}

	public Optional<Schulfach> find(int id) {
		return repo.findById(id);
	}

	public void delete(Schulfach schulfach) {
		repo.delete(schulfach);
	}
}
