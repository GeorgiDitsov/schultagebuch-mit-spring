package com.proxiad.schultagebuch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.entity.Schuler;

@Repository
public interface SchulerRepository extends JpaRepository<Schuler, Integer> {

	public List<Schuler> findAllByOrderByIdAsc();

	public List<Schuler> findByKlasseOrderByIdAsc(Klasse klasse);

	public Optional<Schuler> findByBenutzerBenutzerName(String benutzerName);

	public List<Schuler> findByNameIgnoreCaseLikeOrderByIdAsc(String name);
}
