package com.proxiad.schultagebuch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Benutzer;

@Repository
public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {

	public List<Benutzer> findAllByOrderByIdAsc();

	public Optional<Benutzer> findByBenutzername(String benutzername);

	public List<Benutzer> findByBenutzernameIgnoreCaseLikeOrderByIdAsc(String benutzername);
}
