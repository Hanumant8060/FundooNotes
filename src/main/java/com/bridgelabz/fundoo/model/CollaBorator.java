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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class CollaBorator {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int collabId;
	private String collabEmail;
	@ManyToMany
	@JoinTable(name = "CollaBorator_Notes_join", joinColumns = @JoinColumn(name = "collabEmail"), inverseJoinColumns = @JoinColumn(name = "noteId"))
	@JsonIgnoreProperties(value = "collaboratorList")
	private List<Notes> noteList = new ArrayList<Notes>();

	public String getCollabEmail() {
		return collabEmail;
	}

	public List<Notes> getNoteList() {
		return noteList;
	}

	public void setCollabEmail(String collabEmail) {
		this.collabEmail = collabEmail;
	}

	public void setNoteList(List<Notes> noteList) {
		this.noteList = noteList;
	}

	public int getCollabId() {
		return collabId;
	}

	public void setCollabId(int collabId) {
		this.collabId = collabId;
	}

	@Override
	public String toString() {
		return "CollaBorator [collabId=" + collabId + ", collabEmail=" + collabEmail + ", noteList=" + noteList + "]";
	}

}
