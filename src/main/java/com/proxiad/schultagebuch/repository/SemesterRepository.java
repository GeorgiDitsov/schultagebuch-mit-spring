package com.proxiad.schultagebuch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Integer> {

	public List<Semester> findAllByOrderByIdAsc();
}
