package com.proxiad.schultagebuch.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Schuler;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	public List<Note> findBySchulerAndDatumBetweenOrderByDatumDesc(Schuler schuler, LocalDateTime begin,
			LocalDateTime end);

	public Optional<Note> findFirstBySchulerOrderByDatumDesc(Schuler schuler);

}
