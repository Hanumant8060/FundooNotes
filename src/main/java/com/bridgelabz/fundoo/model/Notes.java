package com.bridgelabz.fundoo.model;

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

@Entity
public class Notes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int noteId;
	private String note_disc;
	private String note_title;
	@ManyToOne
	@JoinColumn(name = "userid")
	private User userid;
	@ManyToMany
	@JoinTable(name = "Label_Notes_join",joinColumns = @JoinColumn(name = "noteId"), inverseJoinColumns = @JoinColumn(name = "labelId"))
	
	private List<Label> labelNoteslist= new ArrayList<Label>();

	

	public Notes() {
		super();
		// TODO Auto-generated constructor stub
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

	@Override
	public String toString() {
		return "Notes [noteId=" + noteId + ", note_disc=" + note_disc + ", note_title=" + note_title + ", userid="
				+ userid + ", labelNoteslist=" + labelNoteslist + "]";
	}
	
	

}
