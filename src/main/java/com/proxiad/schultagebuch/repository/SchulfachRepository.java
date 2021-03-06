package com.proxiad.schultagebuch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Schulfach;

@Repository
public interface SchulfachRepository extends JpaRepository<Schulfach, Long> {

	public List<Schulfach> findAllByOrderByIdAsc();
}
