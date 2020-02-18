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

import com.bridgelabz.fundoo.dto.NotesDto;
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
	public String add(@RequestBody Notes notes,@RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		System.out.println("decodetoken "+decodeToken);
		return service.createNote(notes, decodeToken);	
	}
	@DeleteMapping("/delete")
	public String delete(@RequestBody Notes notes , @RequestHeader String token){
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.deleteNote(notes,decodeToken);
	}
	@GetMapping("/findAll")
		public List<Notes> getNotes(@RequestHeader String token) {
			String decodeToken = tokenService.getUserIdFromToken(token);
			return service.getAllNotes(decodeToken);
	}
	
	@PutMapping("/update")
	public String update(@RequestBody Notes notes,@RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.updateNote(notes,decodeToken);
		
	}

}
