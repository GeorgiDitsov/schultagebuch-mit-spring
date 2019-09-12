package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.repository.LehrerRepository;

@Service
@Transactional
public class LehrerService {

	@Autowired
	private LehrerRepository repo;

	public void save(Lehrer lehrer) {
		repo.save(lehrer);
	}

	public List<Lehrer> findAll() {
		return repo.findAllByOrderByIdAsc();
	}

	public Optional<Lehrer> find(int id) {
		return repo.findById(id);
	}

	public void delete(Lehrer lehrer) {
		repo.delete(lehrer);
	}
}
