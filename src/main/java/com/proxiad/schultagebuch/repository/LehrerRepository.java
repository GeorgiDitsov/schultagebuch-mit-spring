package com.proxiad.schultagebuch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Lehrer;

@Repository
public interface LehrerRepository extends JpaRepository<Lehrer, Long> {

	public List<Lehrer> findAllByOrderByIdAsc();

	public Optional<Lehrer> findByBenutzerBenutzername(String benutzername);

	public List<Lehrer> findByNameIgnoreCaseLikeOrderByIdAsc(String name);
}
