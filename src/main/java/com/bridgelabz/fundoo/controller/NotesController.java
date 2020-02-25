package com.bridgelabz.fundoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.impl.PublicClaims;
import com.bridgelabz.fundoo.model.CollaBorator;
import com.bridgelabz.fundoo.model.Notes;
import com.bridgelabz.fundoo.service.NotesService;
import com.bridgelabz.fundoo.utility.TokenService;

@RestController
@RequestMapping("/note")
public class NotesController {
	@Autowired
	private NotesService service;
	@Autowired
	private TokenService tokenService;

	@PostMapping("/create")
	public String add(@RequestBody Notes notes, @RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		System.out.println("decodetoken " + decodeToken);
		return service.createNote(notes, decodeToken);
	}

	@DeleteMapping("/delete")
	public String delete(@RequestBody Notes notes, @RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.deleteNote(notes, decodeToken);
	}

	@GetMapping("/findAll")
	public List<Notes> getNotes(@RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.getAllNotes(decodeToken);
	}

	@PutMapping("/update")
	public String update(@RequestBody Notes notes, @RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.updateNote(notes, decodeToken);

	}

	@GetMapping("/sort")
	public List<Notes> sortNotes(@RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.sortNotesByName(decodeToken);
	}

	@PostMapping("/trash")
	public String trashNotes(@RequestHeader int noteId, @RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.trashNote(noteId, decodeToken);
	}

	@DeleteMapping("/untrash")
	public String unTrashNotes(@RequestHeader int noteId, @RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.unTrashNote(noteId, decodeToken);
	}

	@PostMapping("/pin")
	public String pin(@RequestHeader int noteId, @RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.pinNote(noteId, decodeToken);
	}

	@PostMapping("/archived")
	public String archived(@RequestHeader int noteId, @RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.archivedNote(noteId, decodeToken);
	}

	@PostMapping("/unarchived")
	public String unArchived(@RequestHeader int noteId, @RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.unArchivedNote(noteId, decodeToken);
	}

	@DeleteMapping("/deleteTrash")
	public String deleteTrash(@RequestHeader String token, @RequestHeader int noteId) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.deleteTrashNote(noteId, decodeToken);

	}

	@GetMapping("/listOfTrash")
	public List<Notes> listOfTrash(@RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.listOfTrashNotes(decodeToken);

	}

	@GetMapping("/listOfArchive")
	public List<Notes> listOfArchive(@RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.listOfArchiveNotes(decodeToken);
	}

	@PostMapping("/collaborator")
	public String collaborate(@RequestHeader String tokens, @RequestHeader String email, @RequestHeader int noteId) {
		String decodeString = tokenService.getUserIdFromToken(tokens);
		return service.collaborate(decodeString, email, noteId);
	}

	@PostMapping("/searchByTitle")
	public List<Notes> searchString(@RequestHeader String token, @RequestHeader String noteTitle) {
		String decoString = tokenService.getUserIdFromToken(token);
		return service.searchByTitle(decoString, noteTitle);

	}

	@GetMapping("/getCollabList")
	public List<CollaBorator> getList(@RequestHeader String token, @RequestHeader int noteId) {
		String decodeString = tokenService.getUserIdFromToken(token);
		return service.getAllCollaborator(decodeString, noteId);
	}

	@PostMapping("/addReminder")
	public String addReminder(@RequestHeader String token, @RequestHeader String time, @RequestHeader int noteId) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.setReminder(decodeToken, time, noteId);
	}

	@DeleteMapping("/removeReminder")
	public String removeReminder(@RequestHeader String token, @RequestHeader int noteId) {
		String decoString = tokenService.getUserIdFromToken(token);
		return service.remove(decoString, noteId);
	}

	@DeleteMapping("/removeCollaborator")
	public String remove(@RequestHeader String token, @RequestHeader int noteId, @RequestHeader String email) {
		String decodeString = tokenService.getUserIdFromToken(token);
		return service.removeCollaborator(decodeString, noteId, email);
	}

}
