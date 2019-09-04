package com.proxiad.schultagebuch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Rolle;

@Repository
public interface RolleRepository extends JpaRepository<Rolle, Integer> {

}
