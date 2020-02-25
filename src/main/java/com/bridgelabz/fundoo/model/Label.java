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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Label {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long labelId;
	private String label_name;
	private LocalDateTime atCreated;
	private LocalDateTime atModified;
	@ManyToOne
	@JoinColumn(name = "userid")
	private User userid;
	@ManyToMany
	@JoinTable(name = "Label_Notes_join", joinColumns = @JoinColumn(name = "labelId"), inverseJoinColumns = @JoinColumn(name = "noteId"))
	@JsonIgnoreProperties(value = "labelNotesList")
	private List<Notes> noteLabelList = new ArrayList<Notes>();

	public Label() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Label(long labelId, String label_name, List<Notes> noteLabelList, User userid) {
		super();
		this.labelId = labelId;
		this.label_name = label_name;
		this.noteLabelList = noteLabelList;
		this.userid = userid;
	}

	public long getLabelId() {
		return labelId;
	}

	public String getLabel_name() {
		return label_name;
	}

	public List<Notes> getNoteLabelList() {
		return noteLabelList;
	}

	public User getUserid() {
		return userid;
	}

	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}

	public void setLabel_name(String label_name) {
		this.label_name = label_name;
	}

	public void setNoteLabelList(List<Notes> noteLabelList) {
		this.noteLabelList = noteLabelList;
	}

	public void setUserid(User userid) {
		this.userid = userid;
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

	@Override
	public String toString() {
		return "Label [labelId=" + labelId + ", label_name=" + label_name + ", noteLabelList=" + noteLabelList
				+ ", userid=" + userid + "]";
	}

}
