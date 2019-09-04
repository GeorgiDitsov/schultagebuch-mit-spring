package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.repository.ElternteilRepository;

@Service
@Transactional
public class ElternteilService {

	@Autowired
	private ElternteilRepository repo;

	public void save(Elternteil elternteil) {
		repo.save(elternteil);
	}

	public List<Elternteil> findAll() {
		return repo.findAllByOrderByIdAsc();
	}

	public Optional<Elternteil> find(int id) {
		return repo.findById(id);
	}

	public void delete(Elternteil elternteil) {
		repo.delete(elternteil);
	}
}
