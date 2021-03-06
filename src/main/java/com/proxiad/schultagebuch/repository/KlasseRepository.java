package com.proxiad.schultagebuch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Klasse;

@Repository
public interface KlasseRepository extends JpaRepository<Klasse, Long> {

	public List<Klasse> findAllByOrderByIdAsc();

}
