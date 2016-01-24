package com.cirs.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Complaint extends CirsEntity {
	public static enum Status {
		NEW
	}

	@Column(name = "complaint_id")
	@Id
	@SequenceGenerator(name = "complaint_id_seq", sequenceName = "complaint_complaint_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "complaint_id_seq")
	private Long id;

	@Column
	private String title;

	@Column
	private String description;

	@Column
	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne(optional = false)
	@JoinColumn(referencedColumnName="category_id", name="category_id")
	private Category category;

	private Timestamp timestamp;

	@ManyToOne(optional = false)
	@JoinColumn(referencedColumnName="id", name="user_id")
	private User user;

	@Override
	public Long getId() {
		return id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Category getCategory() {
		return category;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Complaint [id=" + id + ", title=" + title + ", description=" + description + ", status=" + status
				+ ", category=" + category + ", timestamp=" + timestamp + ", user=" + user + "]";
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
