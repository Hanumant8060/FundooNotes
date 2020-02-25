package com.bridgelabz.fundoo.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.EmitUtils;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.model.CollaBorator;
import com.bridgelabz.fundoo.model.Notes;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.CollabRepository;
import com.bridgelabz.fundoo.repository.NotesRepository;
import com.bridgelabz.fundoo.repository.UserRepository;

@Service
public class NotesService {
	@Autowired
	private NotesRepository notesRepository;
	@Autowired
	private UserRepository userRepositry;
	@Autowired
	private CollabRepository collabRepository;

	public String createNote(Notes notes, String token) {
		Optional<User> user = userRepositry.findByEmail(token);
		if ((notes.getNote_title().isEmpty()) && (notes.getNote_disc().isEmpty())) {
			return "note title/description is empty";
		} else {
			notes.setUserid(user.get());
			notes.setAtCreated();
			notesRepository.save(notes);
		}
		return "note created";

	}

	public String deleteNote(Notes notes, String decodeToken) {
		Optional<User> user = userRepositry.findByEmail(decodeToken);
		Optional<Notes> note = notesRepository.findByNoteId(user.get().getUserid());
		if ((note.isEmpty()) && (user.isEmpty())) {
			return "note not present";
		} else {
			notesRepository.delete(notes);
		}
		return "note deleted";
	}

	public List<Notes> getAllNotes(String decodeToken) {
		// System.out.println(decodeToken);
		List<Notes> allNotes = new ArrayList<Notes>();
		Optional<User> user = userRepositry.findByEmail(decodeToken);
		List<Notes> noteList = notesRepository.findAll();
		// System.out.println(notes.toString());
		allNotes = noteList.stream().filter(i -> i.getUserid().equals(user.get())).collect(Collectors.toList());
		// allNotes = notesRepository.findAll();
		return allNotes;

	}

	public String updateNote(Notes notes, String decodeToken) {
		Optional<Notes> note = notesRepository.findById(notes.getNoteId());
		System.out.println("Note " + note);
		Optional<User> user = userRepositry.findByEmail(decodeToken);
		System.out.println("User " + user);
		if (user.isPresent()) {
			if (note.isPresent()) {
				note.get().setNote_disc(notes.getNote_disc());
				note.get().setNote_title(notes.getNote_title());
				note.get().setAtModified(LocalDateTime.now());
				System.out.println(LocalDateTime.now());
				notesRepository.save(note.get());
			}
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
			// System.out.println("sort successfully ");
		}
		return sortNotelist1;
	}

	public String trashNote(int note, String token) {
		Optional<User> userId = userRepositry.findByEmail(token);
		Optional<Notes> noteId = notesRepository.findByNoteId(note);
		if (userId.isEmpty()) {
			throw new RuntimeException("user not present");
		}
		if (noteId.isEmpty()) {
			throw new RuntimeException("note not present");
		} else {

			if (noteId.get().isPin()) {
				noteId.get().setPin(false);
			}
			noteId.get().setTrash(true);
			notesRepository.save(noteId.get());
		}

		return "note trashed";
	}

	public String unTrashNote(int noteId, String decodeToken) {

		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		Optional<Notes> note = notesRepository.findByNoteId(noteId);
		if (userId.isEmpty()) {
			throw new RuntimeException("user not present");
		}
		if (note.isEmpty()) {
			throw new RuntimeException("note not present");
		} else {

			if (note.get().isTrash()) {
				note.get().setTrash(false);
				notesRepository.save(note.get());
			}
		}
		return "note untrashed";
	}

	public String pinNote(int note, String decodeToken) {
		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		Optional<Notes> noteId = notesRepository.findByNoteId(note);
		// System.out.println("ispin "+noteId.get().isPin());
		if (userId.isEmpty()) {
			throw new RuntimeException("user not present");
		}
		if (noteId.isEmpty()) {
			throw new RuntimeException("note not present");
		} else {
			if (noteId.get().isPin()) {
				// System.out.println("if true ");
				noteId.get().setPin(false);
				notesRepository.save(noteId.get());
			} else {
				// System.out.println("if false ");
				noteId.get().setPin(true);
				notesRepository.save(noteId.get());
				return "pinned";
			}
		}
		return "unpinned";
	}

	public String archivedNote(int noteId, String decodeToken) {
		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		Optional<Notes> note = notesRepository.findByNoteId(noteId);
		if (userId.isEmpty()) {
			throw new RuntimeException("user not present");
		}
		if (note.isEmpty()) {
			throw new RuntimeException("note not present");
		} else {
			if (note.get().isPin()) {
				note.get().setPin(false);
			}
			note.get().setArchive(true);
			notesRepository.save(note.get());
		}
		return "note archived";
	}

	public String unArchivedNote(int noteId, String decodeToken) {
		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		Optional<Notes> note = notesRepository.findByNoteId(noteId);
		if (userId.isEmpty()) {
			throw new RuntimeException("user not present");
		}
		if (note.isEmpty()) {
			throw new RuntimeException("note not present");
		} else {
			if (note.get().isArchive()) {
				note.get().setArchive(false);
				notesRepository.save(note.get());
			}
		}
		return "note unarchived";
	}

	public String deleteTrashNote(int noteId, String decodeToken) {
		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		Optional<Notes> note = notesRepository.findByNoteId(noteId);
		if (userId.isPresent()) {
			if (note.get().isTrash() == true) {
				notesRepository.delete(note.get());
			}
		} else {
			throw new RuntimeException("user/note not present");
		}
		return "trash deleted";
	}

	public List<Notes> listOfTrashNotes(String decodeToken) {
		List<Notes> allTrashNotes = new ArrayList<Notes>();
		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		Optional<Notes> note = notesRepository.findByUserid(userId.get());
		allTrashNotes = note.stream().filter(i -> i.isTrash()).collect(Collectors.toList());
		return allTrashNotes;
	}

	public List<Notes> listOfArchiveNotes(String decodeToken) {
		List<Notes> allArchiveNotes = new ArrayList<Notes>();
		Optional<User> userId = userRepositry.findByEmail(decodeToken);
		Optional<Notes> note = notesRepository.findByUserid(userId.get());
		allArchiveNotes = note.stream().filter(i -> i.isArchive()).collect(Collectors.toList());
		return allArchiveNotes;
	}

	public List<Notes> searchByTitle(String decoString, String noteTitle) {
		// Optional<User> user = userRepositry.findByEmail(decoString);
		// Optional<Notes> note = notesRepository.findByUserid(user.get());
		List<Notes> allNote = getAllNotes(decoString);
		// List<Notes> listOfNotes =
		// allNotes.stream().filter(i->i.getUserid()).collect(Collectors.toList());
		List<Notes> titleList = allNote.stream().filter(i -> (i.getNote_title().equals(noteTitle)))
				.collect(Collectors.toList());
		return titleList;

	}

	public String collaborate(String tokens, String emailid, int noteId) {
		Optional<User> user1 = userRepositry.findByEmail(tokens);
		Optional<Notes> notes = notesRepository.findByNoteId(noteId);
		CollaBorator colaborate = new CollaBorator();
		Optional<CollaBorator> newid = collabRepository.findByCollabEmail(emailid);
		if (!user1.isPresent()) {
			return "user is not present";
		} else if (!notes.isPresent()) {
			return "notes not present";
		}
		if (!newid.isPresent()) {
			System.out.println("hellooolol");
			colaborate.setCollabEmail(emailid);
			colaborate.getNoteList().add(notes.get());
			collabRepository.save(colaborate);
			return "new user added succesfully";
		}
		if (emailid.equals(tokens)) {
			return "owner";
		}
		if (newid.isPresent()) {
			for (int i = 0; i < newid.get().getNoteList().size(); i++) {
				if ((newid.get().getNoteList().get(i).getNoteId()) == noteId) {
					return "already colaborate";
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

	public String setReminder(String decodeToken, String time, int noteId) {
		Optional<User> user = userRepositry.findByEmail(decodeToken);
		System.out.println("get user");
		Optional<Notes> notes = notesRepository.findByNoteId(noteId);
		System.out.println("get note");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		// OffsetDateTime odt = OffsetDateTime.parse ( time,formatter);
		LocalDateTime reminderTime = LocalDateTime.parse(time, formatter);
		if ((user.isPresent()) && (notes.isPresent())) {
			notes.get().setReminder(true);
			notes.get().setReminderTime(reminderTime);
			notesRepository.save(notes.get());
		}
		return "Reminder set successfully";
	}

	public String remove(String decoString, int noteId) {
		Optional<User> user = userRepositry.findByEmail(decoString);
		Optional<Notes> notes = notesRepository.findByNoteId(noteId);
		if ((user.isPresent()) && (notes.isPresent())) {
			notes.get().setReminder(false);
			notes.get().setReminderTime(null);
			notesRepository.save(notes.get());
		}
		return "Reminder remove successfully";
	}

	public String removeCollaborator(String decodeString, int noteId, String emailString) {
		Optional<User> user = userRepositry.findByEmail(decodeString);
		Optional<Notes> notes = notesRepository.findByNoteId(noteId);
		Optional<CollaBorator> collaborator = collabRepository.findByCollabEmail(emailString);
		collaborator.get().getNoteList().remove(emailString);
		return null;
	}
}
