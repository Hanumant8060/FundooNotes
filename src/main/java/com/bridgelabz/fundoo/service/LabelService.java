package com.bridgelabz.fundoo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.LabelException;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.Notes;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.LabelRepository;
import com.bridgelabz.fundoo.repository.NotesRepository;
import com.bridgelabz.fundoo.repository.UserRepository;

@Service
public class LabelService {
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NotesRepository noteRpository;

	public String createLabel(Label label, String decodeToken) throws LabelException {
		Optional<Label> labelname = labelRepository.findByLabelname(label.getLabelname());
		Optional<User> userId = userRepository.findByEmail(decodeToken);
		if (labelname.isEmpty()) {
			label.setUserid(userId.get());
			label.setAtCreated();
			labelRepository.save(label);
		} else {
			throw new LabelException("label already present");
		}
		return "label created";
	}

	public String deleteLabel(Label label, String decodeToken) throws LabelException {
		Optional<User> userId = userRepository.findByEmail(decodeToken);
		Optional<Label> label_id = labelRepository.findByLabelId(label.getLabelId());
		if (label_id.isEmpty()) {
			throw new LabelException("Label not exist");
		}
		if (userId.isEmpty()) {
			throw new LabelException("User not exist");
		} else {
			labelRepository.delete(label);
		}
		return "delete sucesfully";
	}

	public String updateLabel(Label label, String decodeToken) throws LabelException {
		Optional<User> userId = userRepository.findByEmail(decodeToken);
		Optional<Label> label_id = labelRepository.findByLabelId(label.getLabelId());
		if (label_id.isPresent()) {
			label_id.get().setLabelname(label.getLabelname());
			label_id.get().setAtModified(LocalDateTime.now());
			labelRepository.save(label_id.get());
		} else {
			throw new LabelException("label not present");
		}
		return "updated succesfully";
	}

	public List<Label> findAll(String decodeToken) {
		List<Label> allLabels = new ArrayList<Label>();
		Optional<User> user = userRepository.findByEmail(decodeToken);
		if (user.isPresent())
			allLabels = labelRepository.findAll();
		return allLabels;
	}

	public String addLabelToNote(String token, int noteId, int labelId) throws LabelException {
		Optional<Label> label_Id = labelRepository.findByLabelId(labelId);
		Optional<Notes> note_Id = noteRpository.findByNoteId(noteId);
		Optional<User> user_Id = userRepository.findByEmail(token);
		if (label_Id.isEmpty()) {
			if (note_Id.isEmpty()) {
				if (user_Id.isEmpty()) {
					throw new LabelException("Something went wrong");
				}
			}
		} else {
			note_Id.get().getLabelNoteslist().add(label_Id.get());
			noteRpository.save(note_Id.get());
		}
		return "update";

	}

	public String addNoteToLabel(String token, int noteId, int labelId) throws LabelException {
		Optional<Label> label = labelRepository.findByLabelId(labelId);
		Optional<Notes> note_Id = noteRpository.findByNoteId(noteId);
		Optional<User> user_Id = userRepository.findByEmail(token);
		if (label.isEmpty()) {
			if (note_Id.isEmpty()) {
				if (user_Id.isEmpty()) {
					throw new LabelException("Something went wrong");
				}
			}
		} else {
			label.get().getNoteLabelList().add(note_Id.get());
			labelRepository.save(label.get());
		}
		return "update";
	}

	public List<Label> displayaddedlabels(String token, int noteId) {
		Optional<User> user = userRepository.findByEmail(token);
		if (user.isPresent()) {
			List<Label> labelModelAll = labelRepository.findAll();
			List<Label> labelModelUserCreate = labelModelAll.stream()
					.filter(i -> i.getUserid().getUserid() == user.get().getUserid()).collect(Collectors.toList());
			List<Label> sortlabel = new ArrayList<Label>();
			for (int j = 0; j < labelModelUserCreate.size(); j++) {
				for (int k = 0; k < labelModelUserCreate.get(j).getNoteLabelList().size(); k++) {
					if ((labelModelUserCreate.get(j).getNoteLabelList().get(k).getNoteId()) == noteId) {
						sortlabel.add(labelModelUserCreate.get(j));
					}
				}
			}
			return (sortlabel);

		}

		return null;
	}

	public List<Label> displayunaddedlabels(String token, int noteId) {
		Optional<User> registrationModel = userRepository.findByEmail(token);
		Optional<Notes> notesinfo = noteRpository.findByNoteId(noteId);
		if (registrationModel.isPresent()) {
			List<Label> labelModelAll = labelRepository.findAll();
			List<Label> labelModelUserCreate = labelModelAll.stream()
					.filter(i -> (i.getUserid().getUserid() == registrationModel.get().getUserid()))
					.collect(Collectors.toList());
			List<Label> sortLabel = new ArrayList<Label>();
			sortLabel.addAll(labelModelUserCreate);
			for (int j = 0; j < labelModelUserCreate.size(); j++) {
				for (int j2 = 0; j2 < labelModelUserCreate.get(j).getNoteLabelList().size(); j2++) {
					if ((labelModelUserCreate.get(j).getNoteLabelList().get(j2).getNoteId()) == noteId) {
						sortLabel.remove(labelModelUserCreate.get(j));

					}
				}
			}
			return (sortLabel);
		}
		return null;

	}

	public String removelabelfromnote(String tokennew, int noteId, int labelid) {
		Optional<User> registrationModel = userRepository.findByEmail(tokennew);
		if (registrationModel.isPresent()) {
			Optional<Notes> noteModel = noteRpository.findByNoteId(noteId);
			Optional<Label> labelModel = labelRepository.findByLabelId(labelid);
			if (noteModel.isPresent() && labelModel.isPresent()) {
				noteModel.get().getLabelNoteslist().remove(labelModel.get());
				noteRpository.save(noteModel.get());

			}

		}
		return "note remove";

	}

}
