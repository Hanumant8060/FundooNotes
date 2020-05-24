package com.bridgelabz.fundoo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.exception.NotesException;
import com.bridgelabz.fundoo.model.CollaBorator;
import com.bridgelabz.fundoo.model.Notes;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.NotesService;
import com.bridgelabz.fundoo.utility.TokenService;

@RestController
@CrossOrigin
@RequestMapping("/note")
public class NotesController {
	@Autowired
	private NotesService service;
	@Autowired
	private TokenService tokenService;

	@PostMapping("/create")
	public Response add(@Valid @RequestBody Notes notes, @RequestHeader String token) throws NotesException {
		String decodeToken = tokenService.getUserIdFromToken(token);
		service.createNote(notes, decodeToken);
		return new Response("note Created ", "Ok", 200);
	}

	@PostMapping("/delete")
	public Response delete(@RequestBody Notes notes, @RequestHeader String token) throws NotesException {
		String decodeToken = tokenService.getUserIdFromToken(token);
		service.deleteNote(notes, decodeToken);
		return new Response("note deleted", "ok", 200);
	}

	@GetMapping("/findAll")
	public List<Notes> getNotes(@RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.getAllNotes(decodeToken);
	}

	@PutMapping("/update")
	public Response update(@Valid @RequestBody Notes notes, @RequestHeader String token) throws NotesException {
		String decodeToken = tokenService.getUserIdFromToken(token);
		service.updateNote(notes, decodeToken);
		return new Response("note updated", "ok", 200);

	}

	@GetMapping("/sort")
	public List<Notes> sortNotes(@RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.sortNotesByName(decodeToken);
	}

	@PostMapping("/trash")
	public Response trashNotes(@RequestBody Notes noteId, @RequestHeader String token) throws NotesException {
		String decodeToken = tokenService.getUserIdFromToken(token);
		service.trashNote(noteId, decodeToken);
		return new Response("note trashed", "OK", 200);
	}

	@PostMapping("/untrash")
	public Response unTrashNotes(@RequestParam int noteId, @RequestHeader String token) throws NotesException {
		String decodeToken = tokenService.getUserIdFromToken(token);
		service.unTrashNote(noteId, decodeToken);
		return new Response("note untrashed", "OK", 200);
	}

	@PostMapping("/pin")
	public Response pin(@RequestParam int noteId, @RequestHeader String token) throws NotesException {
		String decodeToken = tokenService.getUserIdFromToken(token);
		service.pinNote(noteId, decodeToken);
		return new Response("note pinned", "Ok", 200);
	}

	@PostMapping("/archived")
	public Response archived(@RequestBody Notes noteId, @RequestHeader String token) throws NotesException {
		String decodeToken = tokenService.getUserIdFromToken(token);
		service.archivedNote(noteId, decodeToken);
		return new Response("note archived", "ok", 200);
	}

	@PostMapping("/unarchived")
	public Response unArchived(@RequestParam int noteId, @RequestHeader String token) throws NotesException {
		String decodeToken = tokenService.getUserIdFromToken(token);
		service.unArchivedNote(noteId, decodeToken);
		return new Response("note unarchived", "ok", 200);
	}

	@PostMapping("/deleteTrash")
	public Response deleteTrash(@RequestHeader String token, @RequestParam int noteId) throws NotesException {
		String decodeToken = tokenService.getUserIdFromToken(token);
		service.deleteTrashNote(noteId, decodeToken);
		return new Response("note deleted from trash", "ok", 200);

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
	public Response collaborate(@RequestHeader String token, @RequestParam String email, @RequestParam int noteId)
			throws NotesException {
		String decodeString = tokenService.getUserIdFromToken(token);
		service.collaborate(decodeString, email, noteId);
		return new Response("user collaborate", "ok", 200);
	}

	@GetMapping("/searchByTitle")
	public Response searchString(@RequestParam String token, @RequestParam String noteTitle) {
		String decoString = tokenService.getUserIdFromToken(token);
		return service.searchByTitle(decoString, noteTitle);

	}

//
	@GetMapping("/getCollabList")
	public List<CollaBorator> getList(@RequestParam String token, @RequestParam int noteId) {
		String decodeString = tokenService.getUserIdFromToken(token);
		System.out.println("collaborator ls");
		return service.getAllCollaborator(decodeString, noteId);
	}

	@PostMapping("/addReminder")
	public Response addReminder(@RequestHeader String token, @RequestParam String time, @RequestParam int noteId)
			throws NotesException {
		String decodeToken = tokenService.getUserIdFromToken(token);
		service.setReminder(decodeToken, time, noteId);
		return new Response("reminder set successfully", "ok", 200);
	}

	@PostMapping("/removeReminder")
	public Response removeReminder(@RequestHeader String token, @RequestParam int noteId) throws NotesException {
		String decoString = tokenService.getUserIdFromToken(token);
		service.remove(decoString, noteId);
		return new Response("reminder remove", "ok", 200);

	}

	@GetMapping("/listOfReminder")
	public List<Notes> listOfReminder(@RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.listOfReminderNotes(decodeToken);

	}

	@PostMapping("/backgroundColor")
	public Response setBackgroundColor(@RequestBody Notes noteModel, @RequestParam String token) {
		String decodeString = tokenService.getUserIdFromToken(token);
		service.Backgroundcolor(noteModel, decodeString);
		return new Response(" color set succesfully", "OK", 200);

	}

}
