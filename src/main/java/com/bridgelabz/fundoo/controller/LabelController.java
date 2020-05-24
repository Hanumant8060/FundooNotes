package com.bridgelabz.fundoo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.exception.LabelException;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.LabelService;
import com.bridgelabz.fundoo.utility.TokenService;

@RestController
@CrossOrigin
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

	@PostMapping("/delete")
	public Response delete(@RequestBody Label label, @RequestHeader String token) throws LabelException {
		String decodeToken = tokenService.getUserIdFromToken(token);
		service.deleteLabel(label, decodeToken);
		return new Response("label deleted", "done", 200);

	}

	@PostMapping("/update")
	public Response update(@RequestBody Label label, @RequestHeader String token) throws LabelException {
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
	public Response add(@RequestHeader String decodeToken, @RequestParam int noteId, @RequestParam int labelId)
			throws LabelException {
		String token = tokenService.getUserIdFromToken(decodeToken);
		service.addLabelToNote(token, noteId, labelId);
		return new Response("label added to note", "OK", 200);

	}

	@PostMapping("/addNoteToLabel")
	public Response addNote(@RequestHeader String decodeToken, @RequestHeader int noteId, @RequestHeader int labelId)
			throws LabelException {
		String token = tokenService.getUserIdFromToken(decodeToken);
		service.addNoteToLabel(token, noteId, labelId);
		return new Response("note added to label", "OK", 200);

	}

	@GetMapping("displaylabeladdedtonote")
	public List<Label> LabelAddedToNote(@RequestParam String token, @RequestParam int noteId) {
		String newToken = tokenService.getUserIdFromToken(token);
		return service.displayaddedlabels(newToken, noteId);

	}

	@GetMapping(value = "/DisplayUnAddedlabel")
	public List<Label> displayLabelUnAdded(@RequestParam String token, @RequestParam int noteId) {
		String newToken = tokenService.getUserIdFromToken(token);
		return service.displayunaddedlabels(newToken, noteId);
	}

	@PostMapping("/removeLabelFromNotes")
	public Response removeToLabelFromNote(@RequestParam int noteId, @RequestParam int labelId,
			@RequestParam String token) {
		String tokennew = tokenService.getUserIdFromToken(token);
		service.removelabelfromnote(tokennew, noteId, labelId);
		return new Response("label remove from note succesfully ", "OK", 200);
	}

}
