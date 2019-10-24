package com.proxiad.schultagebuch.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Integer> {

	static final String SEMESTER_BETWEEN_DATES_QUERY = "SELECT s FROM Semester s WHERE s.semesterbeginn <= ?1 AND s.semesterende >= ?1";

	public List<Semester> findAllByOrderByIdAsc();

	@Query(SEMESTER_BETWEEN_DATES_QUERY)
	public Optional<Semester> findBySemesterbeginnBeforeAndSemesterendeAfter(LocalDateTime currentDate);

}
