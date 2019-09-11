package com.proxiad.schultagebuch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Rolle;

@Repository
public interface RolleRepository extends JpaRepository<Rolle, Integer> {

	public List<Rolle> findAllByOrderByIdAsc();

	public Optional<Rolle> findByName(String rolleName);

}
