package com.bridgelabz.fundoo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.exception.LabelException;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.LabelService;
import com.bridgelabz.fundoo.utility.TokenService;

@RestController
@RequestMapping("/label")
public class LabelController {
	@Autowired
	private LabelService service;
	@Autowired
	private TokenService tokenService;

	@PostMapping("/addlabel")
	public Response addLabel(@Valid @RequestBody Label label, @RequestHeader String token) throws LabelException {
		String decodeToken = tokenService.getUserIdFromToken(token);
		service.createLabel(label, decodeToken);
		return new Response("label added", "Ok", 200);

	}

	@DeleteMapping("/delete")
	public Response delete(@RequestBody Label label, @RequestHeader String token) throws LabelException {
		String decodeToken = tokenService.getUserIdFromToken(token);
		service.deleteLabel(label, decodeToken);
		return new Response("label deleted", "done", 200);

	}

	@PutMapping("/update")
	public Response update(@Valid @RequestBody Label label, @RequestHeader String token) throws LabelException {
		String decodeToken = tokenService.getUserIdFromToken(token);
		service.updateLabel(label, decodeToken);
		return new Response("label updated", "OK", 200);
	}

	@GetMapping("/findAll")
	public List<Label> getLabels(@RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.findAll(decodeToken);

	}

	@PostMapping("/addLabelToNote")
	public Response add(@RequestHeader String decodeToken, @RequestHeader int noteId, @RequestHeader int labelId) throws LabelException {
		String token = tokenService.getUserIdFromToken(decodeToken);
		service.addLabelToNote(token, noteId, labelId);
		return new Response("label added to note", "OK", 200);

	}

	@PostMapping("/addNoteToLabel")
	public Response addNote(@RequestHeader String decodeToken, @RequestHeader int noteId, @RequestHeader int labelId) throws LabelException {
		String token = tokenService.getUserIdFromToken(decodeToken);
		service.addNoteToLabel(token, noteId, labelId);
		return new Response("note added to label", "OK", 200);

	}

}
