package com.proxiad.schultagebuch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Elternteil;

@Repository
public interface ElternteilRepository extends JpaRepository<Elternteil, Integer> {

	public List<Elternteil> findAllByOrderByIdAsc();
}
