package com.bridgelabz.fundoo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo.exception.NotesException;
import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.model.CollaBorator;
import com.bridgelabz.fundoo.model.Notes;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.CollabRepository;
import com.bridgelabz.fundoo.repository.NotesRepository;
import com.bridgelabz.fundoo.repository.UserRepository;
import com.bridgelabz.fundoo.response.Response;

@Service
public class NotesService {
	@Autowired
	private NotesRepository notesRepository;
	@Autowired
	private UserRepository userRepositry;
	@Autowired
	private CollabRepository collabRepository;

	public String createNote(Notes notes, String token) throws NotesException {
		Optional<User> user = userRepositry.findByEmail(token);
		if (notes.getNote_title().isEmpty()) {
			throw new NotesException("note title should not be empty");
		}
		if (notes.getNote_disc().isEmpty()) {
			throw new NotesException("note discription should not be empty");

		} else {
			notes.setUserid(user.get());
			notes.setAtCreated();
			notesRepository.save(notes);
		}
		return "note created";

	}

	public String deleteNote(Notes notes, String decodeToken) throws NotesException {
		Optional<User> user = userRepositry.findByEmail(decodeToken);
		Optional<Notes> note = notesRepository.findByNoteId(user.get().getUserid());
		if ((note.isEmpty()) && (user.isEmpty())) {
			throw new NotesException("note not present");
		} else {
			notesRepository.delete(notes);
		}
		return "note deleted";
	}

	public List<Notes> getAllNotes(String decodeToken) {
		List<Notes> allNotes = new ArrayList<Notes>();
		Optional<User> user = userRepositry.findByEmail(decodeToken);
		List<Notes> noteList = notesRepository.findAll();
		allNotes = noteList.stream().filter(i -> i.getUserid().equals(user.get())).collect(Collectors.toList());
		List<Notes> checkTrash = allNotes.stream().filter(i -> i.isTrash() == false).collect(Collectors.toList());
		List<Notes> checkArchived = checkTrash.stream().filter(i -> i.isArchive() == false)
				.collect(Collectors.toList());
		return checkArchived;

	}

	public String updateNote(Notes notes, String decodeToken) throws NotesException {
		Optional<Notes> note = notesRepository.findById(notes.getNoteId());
		Optional<User> user = userRepositry.findByEmail(decodeToken);
		if ((user.isPresent()) && (note.isPresent())) {
			note.get().setNote_disc(notes.getNote_disc());
			note.get().setNote_title(notes.getNote_title());
			note.get().setAtModified(LocalDateTime.now());
			notesRepository.save(note.get());
		} else {
			throw new NotesException("note not present");
		}
		return "updated successfully";
	}

	public List<Notes> sortNotesByName(String decodeToken) {
		List<Notes> sortNotelist1 = new ArrayList<Notes>();
		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		if (userId.isPresent()) {
			List<Notes> note1 = getAllNotes(decodeToken);
			sortNotelist1 = note1.stream().sorted(Comparator.comparing(Notes::getNote_title))
					.collect(Collectors.toList());
		}
		return sortNotelist1;
	}

	public String trashNote(Notes note, String token) throws NotesException {
		Optional<User> userId = userRepositry.findByEmail(token);
		Optional<Notes> noteId = notesRepository.findByNoteId(note.getNoteId());
		if (userId.isEmpty()) {
			throw new UserException("user not present");
		}
		if (noteId.isEmpty()) {
			throw new NotesException("note not present");
		} else {

			if (noteId.get().isPin()) {
				noteId.get().setPin(false);
			}
			noteId.get().setTrash(true);
			notesRepository.save(noteId.get());
		}

		return "note trashed";
	}

	public String unTrashNote(int noteId, String decodeToken) throws NotesException {

		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		Optional<Notes> note = notesRepository.findByNoteId(noteId);
		if (userId.isEmpty()) {
			throw new UserException("user not present");
		}
		if (note.isEmpty()) {
			throw new NotesException("note not present");
		} else {

			if (note.get().isTrash()) {
				note.get().setTrash(false);
				notesRepository.save(note.get());
			}
		}
		return "note untrashed";
	}

	public String pinNote(int note, String decodeToken) throws NotesException {
		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		Optional<Notes> noteId = notesRepository.findByNoteId(note);
		if (userId.isEmpty()) {
			throw new UserException("user not present");
		}
		if (noteId.isEmpty()) {
			throw new NotesException("note not present");
		} else {
			if (!noteId.get().isPin()) {
				noteId.get().setPin(true);
				notesRepository.save(noteId.get());
			} else {
				noteId.get().setPin(false);
				notesRepository.save(noteId.get());
				return "pinned";
			}
		}
		return "unpinned";
	}

	public String archivedNote(Notes noteId, String decodeToken) throws NotesException {
		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		Optional<Notes> note = notesRepository.findByNoteId(noteId.getNoteId());
		if (userId.isEmpty()) {
			throw new UserException("user not present");
		}
		if (note.isEmpty()) {
			throw new NotesException("note not present");
		} else {
			if (note.get().isPin()) {
				note.get().setPin(false);
			}
			note.get().setArchive(true);
			notesRepository.save(note.get());
		}
		return "note archived";
	}

	public String unArchivedNote(int noteId, String decodeToken) throws NotesException {
		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		Optional<Notes> note = notesRepository.findByNoteId(noteId);
		if (userId.isEmpty()) {
			throw new UserException("user not present");
		}
		if (note.isEmpty()) {
			throw new NotesException("note not present");
		} else {
			if (note.get().isArchive()) {
				note.get().setArchive(false);
				notesRepository.save(note.get());
			}
		}
		return "note unarchived";
	}

	public String deleteTrashNote(int noteId, String decodeToken) throws NotesException {
		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		Optional<Notes> note = notesRepository.findByNoteId(noteId);
		if (userId.isPresent()) {
			if (note.get().isTrash() == true) {
				notesRepository.delete(note.get());
			}
		} else {
			throw new NotesException("note not present");
		}
		return "trash deleted";
	}

	public List<Notes> listOfTrashNotes(String decodeToken) {
		List<Notes> allTrashNotes = new ArrayList<Notes>();
		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		List<Notes> note = notesRepository.findByUserid(userId.get());
		allTrashNotes = note.stream().filter(i -> i.isTrash()).collect(Collectors.toList());
		return allTrashNotes;
	}

	public List<Notes> listOfArchiveNotes(String decodeToken) {
		List<Notes> allArchiveNotes = new ArrayList<Notes>();
		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		List<Notes> note = notesRepository.findByUserid(userId.get());
		allArchiveNotes = note.stream().filter(i -> i.isArchive()).collect(Collectors.toList());
		return allArchiveNotes;
	}

	public Response searchByTitle(String emailid, String title) {
		Optional<User> user = userRepositry.findByEmail(emailid);
		List<Notes> notes = notesRepository.findByUserid(user.get());
		List<Notes> notesList = notes.stream().filter(i -> i.getUserid().getEmail().equals(emailid))
				.collect(Collectors.toList());
		List<Notes> noteSearch = notesList.stream().filter(i -> i.getNote_title().equals(title))
				.collect(Collectors.toList());
		return new Response("display Serach Note successfully", noteSearch, 200);
	}

	public String collaborate(String tokens, String emailid, int noteId) throws NotesException {
		Optional<User> user1 = userRepositry.findByEmail(tokens);
		Optional<Notes> notes = notesRepository.findByNoteId(noteId);
		CollaBorator colaborate = new CollaBorator();
		Optional<CollaBorator> newid = collabRepository.findByCollabEmail(emailid);
		if (!user1.isPresent()) {
			throw new UserException("user is not present");
		} else if (!notes.isPresent()) {
			throw new NotesException("notes not present");
		}
		if (!newid.isPresent()) {
			colaborate.setCollabEmail(emailid);
			colaborate.getNoteList().add(notes.get());
			collabRepository.save(colaborate);
			return "new user added succesfully";
		}
		if (emailid.equals(tokens)) {
			throw new UserException("user is already collaborate");
		}
		if (newid.isPresent()) {
			for (int i = 0; i < newid.get().getNoteList().size(); i++) {
				if ((newid.get().getNoteList().get(i).getNoteId()) == noteId) {
					throw new UserException("already colaborate");
				}
			}
			newid.get().getNoteList().add(notes.get());
			collabRepository.save(newid.get());
		}
		return "collaborate";
	}

	public List<CollaBorator> getAllCollaborator(String decodeToken, int noteId) {
		Optional<Notes> list = notesRepository.findByNoteId(noteId);
		List<CollaBorator> collablist = list.get().getCollaboratorList();
		return collablist;

	}

	public String setReminder(String decodeToken, String time, int noteId) throws NotesException {
		Optional<User> user = userRepositry.findByEmail(decodeToken);
		Optional<Notes> notes = notesRepository.findByNoteId(noteId);
		if (user.isEmpty()) {
			throw new UserException("User not present");
		}
		if (notes.isEmpty()) {
			throw new NotesException("Note not present");

		}
		if ((user.isPresent()) && (notes.isPresent())) {
			notes.get().setReminder(true);
			notes.get().setReminderTime(time);
			notesRepository.save(notes.get());
		}
		return "Reminder set successfully";
	}

	public String remove(String decoString, int noteId) throws NotesException {
		Optional<User> user = userRepositry.findByEmail(decoString);
		Optional<Notes> notes = notesRepository.findByNoteId(noteId);
		if (user.isEmpty()) {
			throw new UserException("User not present");
		}
		if (notes.isEmpty()) {
			throw new NotesException("Note not present");

		}
		if ((user.isPresent()) && (notes.isPresent())) {
			notes.get().setReminder(false);
			notes.get().setReminderTime(null);
			notesRepository.save(notes.get());
		}
		return "Reminder remove successfully";
	}

	public List<Notes> listOfReminderNotes(String decodeToken) {
		List<Notes> allReminderNotes = new ArrayList<Notes>();
		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		List<Notes> note = notesRepository.findByUserid(userId.get());
		allReminderNotes = note.stream().filter(i -> i.isReminder()).collect(Collectors.toList());
		return allReminderNotes;
	}

	public String Backgroundcolor(Notes noteModel, String decodeString) {
		Optional<User> registrationModel = userRepositry.findByEmail(decodeString);
		if (registrationModel.isPresent()) {
			Optional<Notes> note = notesRepository.findById(noteModel.getNoteId());
			note.get().setColor(noteModel.getColor());
			notesRepository.save(note.get());
			return "colorChangeSuccessFully";
		}
		return "not valid token";
	}

}
