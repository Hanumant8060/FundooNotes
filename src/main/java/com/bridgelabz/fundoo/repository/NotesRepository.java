package com.bridgelabz.fundoo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.model.Notes;
import com.bridgelabz.fundoo.model.User;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Integer> {
	Optional<Notes> findByNoteId(int noteId);
	List<Notes> findByUserid(User userId);
	

 }
