package com.proxiad.schultagebuch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Schulstunde;

@Repository
public interface SchulstundeRepository extends JpaRepository<Schulstunde, Integer> {
	public List<Schulstunde> findAllByOrderByKlasseIdAscIdAsc();
}
