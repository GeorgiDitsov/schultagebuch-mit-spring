package com.proxiad.schultagebuch.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.repository.LehrerRepository;

@Service
@Transactional
public class LehrerService {

	@Autowired
	private LehrerRepository repo;

	@Autowired
	private MessageSource messageSource;

	public void save(Lehrer lehrer) {
		repo.save(lehrer);
	}

	public List<Lehrer> findAll() {
		return repo.findAllByOrderByIdAsc();
	}

	public Lehrer find(int id, final Locale locale) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
				messageSource.getMessage("invalid.teacher", new Object[] { id }, locale)));
	}

	public void delete(Lehrer lehrer) {
		repo.delete(lehrer);
	}
}
