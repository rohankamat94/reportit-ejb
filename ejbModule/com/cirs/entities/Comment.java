package com.cirs.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cirs.entities.Complaint.ComplaintTO;
import com.cirs.entities.User.UserTO;

@Entity
@Table(name = "comment")
public class Comment extends CirsEntity {

	@Id
	@SequenceGenerator(name = "comment_id_seq", sequenceName = "comment_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "comment_id_seq")
	@Column(name = "comment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	@ManyToOne(targetEntity = Complaint.class)
	@JoinColumn(referencedColumnName = "complaint_id", name = "complaint_id")
	private Complaint complaint;

	private Timestamp time;

	private String data;

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public CommentTO getCommentTO() {
		CommentTO to = new CommentTO();
		to.data = data;
		to.time = time;
		ComplaintTO cTo = new ComplaintTO();
		cTo.setId(complaint.getId());
		to.complaint = cTo;
		if (user != null) {
			UserTO uTo = new UserTO();
			uTo.setId(user.getId());
			uTo.setFirstName(user.getFirstName());
			uTo.setLastName(user.getLastName());
			to.user = uTo;
		}

		return to;
	}

	public static class CommentTO {
		private UserTO user;

		private ComplaintTO complaint;

		private Timestamp time;

		private String data;
	}

	@Override
	public Object getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Complaint getComplaint() {
		return complaint;
	}

	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}

}
