package com.cirs.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "upvote")
public class Upvote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3336573166193522899L;

	@Id
	@SequenceGenerator(name = "upvote_id_seq", sequenceName = "upvote_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "upvote_id_seq", strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@JoinColumn(referencedColumnName = "id", name = "user_id", nullable = false, updatable = false)
	@ManyToOne
	private User user;

	@JoinColumn(referencedColumnName = "complaint_id", name = "complaint_id", nullable = false, updatable = false)
	@ManyToOne
	private Complaint complaint;

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Complaint getComplaint() {
		return complaint;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}

}
