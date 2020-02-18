package com.bridgelabz.fundoo.dto;

public class LabelDto {
	private long label_id;
	private String label_name;

	public long getLabel_id() {
		return label_id;
	}

	public void setLabel_id(long label_id) {
		this.label_id = label_id;
	}

	public String getLabel_name() {
		return label_name;
	}

	public void setLabel_name(String label_name) {
		this.label_name = label_name;
	}

	@Override
	public String toString() {
		return "LabelDto [label_id=" + label_id + ", label_name=" + label_name + "]";
	}

}
