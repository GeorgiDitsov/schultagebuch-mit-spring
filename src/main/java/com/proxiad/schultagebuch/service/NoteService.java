package com.proxiad.schultagebuch.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Note;
import com.proxiad.schultagebuch.entity.Schuler;
import com.proxiad.schultagebuch.repository.NoteRepository;
import com.proxiad.schultagebuch.view.NoteViewModel;

@Service
@Transactional
public class NoteService {

	@Autowired
	private NoteRepository repo;

	public void save(Note note) {
		repo.save(note);
	}

	public List<NoteViewModel> findBySchuler(Schuler schuler) {
		List<NoteViewModel> notenView = new ArrayList<>();
		repo.findBySchulerOrderByDatumDesc(schuler).stream().forEach(note -> {
			notenView.add(new NoteViewModel(note.getSchulstunde().getSchulfach().getName(),
					note.getSchulstunde().getLehrer().getName(), note.getWert(), note.getDatum()));
		});
		return notenView;
	}

	public void delete(Note note) {
		repo.delete(note);
	}
}
