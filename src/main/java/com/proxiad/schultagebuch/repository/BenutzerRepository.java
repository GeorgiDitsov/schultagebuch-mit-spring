package com.proxiad.schultagebuch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Benutzer;

@Repository
public interface BenutzerRepository extends JpaRepository<Benutzer, Integer> {

	public List<Benutzer> findAllByOrderByIdAsc();

	public Optional<Benutzer> findByBenutzerName(String benutzerName);

	public List<Benutzer> findByBenutzerNameIgnoreCaseLikeOrderByIdAsc(String benutzerName);
}
