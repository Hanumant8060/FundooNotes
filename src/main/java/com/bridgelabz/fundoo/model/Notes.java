package com.bridgelabz.fundoo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Notes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int noteId;
	private String note_disc;
	@NotEmpty(message = "note title should not be empty")
	private String note_title;
	@NotNull
	private boolean isPin;
	@NotNull
	private boolean isTrash;
	@NotNull
	private boolean isArchive;
	private LocalDateTime atCreated;
	private LocalDateTime atModified;
	private boolean isReminder;
	private String reminderTime;
	private String color;

	@ManyToOne
	@JoinColumn(name = "userid")
	private User userid;
	@ManyToMany
	@JoinTable(name = "Label_Notes_join", joinColumns = @JoinColumn(name = "noteId"), inverseJoinColumns = @JoinColumn(name = "labelId"))
	@JsonIgnoreProperties(value = "noteLabelList")
	private List<Label> labelNoteslist = new ArrayList<Label>();
	@ManyToMany
	@JoinTable(name = "User_Notes_join", joinColumns = @JoinColumn(name = "noteId"), inverseJoinColumns = @JoinColumn(name = "userid"))
	@JsonIgnoreProperties(value = "noteUserList")
	private List<User> userNoteList = new ArrayList<User>();
	@ManyToMany
	@JoinTable(name = "CollaBorator_Notes_join", joinColumns = @JoinColumn(name = "noteId"), inverseJoinColumns = @JoinColumn(name = "collabEmail"))
	@JsonIgnoreProperties(value = "noteList")
	private List<CollaBorator> collaboratorList;

	public Notes() {
		super();

	}

	public Notes(int noteId, String note_disc, String note_title, User userid, List<Label> labelNoteslist) {
		super();
		this.noteId = noteId;
		this.note_disc = note_disc;
		this.note_title = note_title;
		this.userid = userid;
		this.labelNoteslist = labelNoteslist;
	}

	public List<Label> getLabelNoteslist() {
		return labelNoteslist;
	}

	public void setLabelNoteslist(List<Label> labelNoteslist) {
		this.labelNoteslist = labelNoteslist;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getNote_disc() {
		return note_disc;
	}

	public void setNote_disc(String note_disc) {
		this.note_disc = note_disc;
	}

	public String getNote_title() {
		return note_title;
	}

	public void setNote_title(String note_title) {
		this.note_title = note_title;
	}

	public User getUserid() {
		return userid;
	}

	public void setUserid(User userid) {
		this.userid = userid;
	}

	public boolean isPin() {
		return isPin;
	}

	public boolean isTrash() {
		return isTrash;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public List<User> getUserNoteList() {
		return userNoteList;
	}

	public void setUserNoteList(List<User> userNoteList) {
		this.userNoteList = userNoteList;
	}

	public List<CollaBorator> getCollaboratorList() {
		return collaboratorList;
	}

	public void setCollaboratorList(List<CollaBorator> collaboratorList) {
		this.collaboratorList = collaboratorList;
	}

	public LocalDateTime getAtCreated() {
		return atCreated;
	}

	public LocalDateTime getAtModified() {
		return atModified;
	}

	public void setAtCreated() {
		this.atCreated = LocalDateTime.now();
	}

	public void setAtModified(LocalDateTime atModified) {
		this.atModified = atModified;
	}

	public boolean isReminder() {
		return isReminder;
	}

	public void setReminder(boolean isReminder) {
		this.isReminder = isReminder;
	}

	public String getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(String reminderTime) {
		this.reminderTime = reminderTime;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
