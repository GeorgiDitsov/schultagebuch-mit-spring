package com.proxiad.schultagebuch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Klasse;
import com.proxiad.schultagebuch.entity.Lehrer;
import com.proxiad.schultagebuch.entity.Schulstunde;

@Repository
public interface SchulstundeRepository extends JpaRepository<Schulstunde, Long> {

	public List<Schulstunde> findAllByOrderByKlasseIdAscIdAsc();

	public List<Schulstunde> findByLehrerOrderByKlasseIdAscIdAsc(Lehrer lehrer);

	public List<Schulstunde> findByKlasseOrderByIdAsc(Klasse klasse);
}
