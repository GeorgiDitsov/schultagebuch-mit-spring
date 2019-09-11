package com.proxiad.schultagebuch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Lehrer;

@Repository
public interface LehrerRepository extends JpaRepository<Lehrer, Integer> {

	public List<Lehrer> findAllByOrderByIdAsc();
}
