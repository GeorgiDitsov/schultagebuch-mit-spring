package com.proxiad.schultagebuch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Elternteil;

@Repository
public interface ElternteilRepository extends JpaRepository<Elternteil, Long> {

	public List<Elternteil> findAllByOrderByIdAsc();

	public Optional<Elternteil> findByBenutzerBenutzername(String benutzername);

	public List<Elternteil> findByNameIgnoreCaseLikeOrderByIdAsc(String name);

}
