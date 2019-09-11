package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.repository.SchulerRepository;

@Service
@Transactional
public class SchulerService {

	@Autowired
	private SchulerRepository repo;

	public void save(Schuler schuler) {
		repo.save(schuler);
	}

	public List<Schuler> findAll() {
		return repo.findAllByOrderByIdAsc();
	}

	public Optional<Schuler> find(int id) {
		return repo.findById(id);
	}

	public void delete(Schuler schuler) {
		repo.delete(schuler);
	}

}
