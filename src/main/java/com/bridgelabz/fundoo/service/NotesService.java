package com.bridgelabz.fundoo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.model.Notes;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.NotesRepository;
import com.bridgelabz.fundoo.repository.UserRepository;

@Service
public class NotesService {
	@Autowired
	private NotesRepository notesRepository;
	@Autowired
	private UserRepository userRepositry;

	public String createNote(Notes notes, String token) {
		Optional<User> user = userRepositry.findByEmail(token);
		if ((notes.getNote_title().isEmpty()) && (notes.getNote_disc().isEmpty())) {
			return "akshada";
		} else {
			notes.setUserid(user.get());
			notesRepository.save(notes);
		}
		return "note created";

	}

	public String deleteNote(Notes notes, String decodeToken) {
		Optional<User> user = userRepositry.findByEmail(decodeToken);
		//Optional<Notes> note = notesRepository.findById(user.get().getUserid());
		Optional<Notes>note=notesRepository.findByNoteId(user.get().getUserid());
		if ((note.isEmpty()) && (user.isEmpty())) {
			return "note not present";
		} else {
			notesRepository.delete(notes);
		}
		return "note deleted";
	}

	public List<Notes> getAllNotes(String decodeToken) {
		List<Notes> allNotes = new ArrayList<Notes>();
		Optional<User> user = userRepositry.findByEmail(decodeToken);

		if (user.isPresent())
			allNotes = notesRepository.findAll();
		return allNotes;

	}

	public String updateNote(Notes notes, String decodeToken) {
		Optional<Notes> note = notesRepository.findById(notes.getNoteId());
		System.out.println("Note " +note);
		Optional<User> user = userRepositry.findByEmail(decodeToken);
		System.out.println("User "+user);
		if (user.isPresent()) {
			if (note.isPresent()) {
				note.get().setNote_disc(notes.getNote_disc());
				note.get().setNote_title(notes.getNote_title());
				notesRepository.save(note.get());
			}
		}
		return "updated successfully";
	}

}
