package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "instructor_detail")
public class InstructorDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "youtube_Chennal")
	private String youTubeChennal;

	@Column(name = "hobby")
	private String hobby;

	public String getYouTubeChennal() {
		return youTubeChennal;
	}

	public void setYouTubeChennal(String youTubeChennal) {
		this.youTubeChennal = youTubeChennal;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public InstructorDetail(String youTubeChennal, String hobby) {
		super();
		this.youTubeChennal = youTubeChennal;
		this.hobby = hobby;
	}

	@Override
	public String toString() {
		return "InstructorDetail [id=" + id + ", youTubeChennal=" + youTubeChennal + ", hobby=" + hobby + "]";
	}

}
