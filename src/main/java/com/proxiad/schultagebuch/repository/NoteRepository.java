package com.proxiad.schultagebuch.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.entity.Schulstunde;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

	public List<Note> findAllByOrderByNoteUpdateDatumDesc();

	public List<Note> findBySchulerAndNoteUpdateDatumBetweenOrderByNoteUpdateDatumDesc(Schuler schuler,
			LocalDateTime begin, LocalDateTime end);

	public List<Note> findBySchulerAndSchulstundeAndNoteUpdateDatumBetween(Schuler schuler, Schulstunde schulstunde,
			LocalDateTime begin, LocalDateTime end);

	public Optional<Note> findFirstBySchulerOrderByNoteUpdateDatumDesc(Schuler schuler);

}
