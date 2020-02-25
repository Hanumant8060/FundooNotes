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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.model.Label;
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
	public String addLabel(@RequestBody Label label, @RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.createLabel(label, decodeToken);

	}

	@DeleteMapping("/delete")
	public String delete(@RequestBody Label label, @RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.deleteLabel(label, decodeToken);

	}
	@PutMapping("/update")
	public String update(@RequestBody Label label, @RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.updateLabel(label, decodeToken);
	}
	@GetMapping("/findAll")
	public List<Label> getLabels(@RequestHeader String token) {
		String decodeToken = tokenService.getUserIdFromToken(token);
		return service.findAll(decodeToken);
		
	}
	@PostMapping("/addLabelToNote")
	public String add(@RequestHeader String decodeToken,@RequestHeader int  noteId,@RequestHeader int  labelId) {
		String token = tokenService.getUserIdFromToken(decodeToken);
		return service.addLabelToNote(token, noteId, labelId);
		
	}
	@PostMapping("/addNoteToLabel")
	public String addNote(@RequestHeader String decodeToken,@RequestHeader int  noteId,@RequestHeader int  labelId) {
		String token = tokenService.getUserIdFromToken(decodeToken);
		return service.addNoteToLabel(token, noteId, labelId);
		
	}
	

}
