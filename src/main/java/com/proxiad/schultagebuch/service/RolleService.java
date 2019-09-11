package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Rolle;
import com.proxiad.schultagebuch.repository.RolleRepository;

@Service
@Transactional
public class RolleService {

	@Autowired
	private RolleRepository repo;

	public List<Rolle> findAll() {
		return repo.findAllByOrderByIdAsc();
	}

	public Optional<Rolle> find(String rolleName) {
		return repo.findByName(rolleName);
	}

}
