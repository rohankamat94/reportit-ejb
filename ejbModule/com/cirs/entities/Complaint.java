package com.cirs.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cirs.entities.Comment.CommentTO;
import com.cirs.entities.User.UserTO;

@Entity
@Table(name = "complaint")
@NamedQueries({
		@NamedQuery(name = Complaint.FIND_BY_ADMIN, query = "SELECT c FROM Complaint c where c.user.admin.id=:adminId") })
public class Complaint extends CirsEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9199809379768333572L;
	public static final String FIND_BY_ADMIN = "findComplaintByAdmin";

	public static enum Status {
		PENDING("Approve", "Reject", "Duplicate"), INPROGRESS("Complete", "Duplicate"), COMPLETED, DUPLICATE, REJECTED;
		private String[] actionsLabel;
		Status(String... actions) {
			actionsLabel = actions;
		}

		@Override
		public String toString() {
			if (this == INPROGRESS) {
				return "IN PROGRESS";
			}
			return super.toString();
		}
		
		public String[] getActionsLabel() {
			return actionsLabel;
		}

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
	@JoinColumn(referencedColumnName = "category_id", name = "category_id")
	private Category category;

	private Timestamp timestamp;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "id", name = "user_id")
	private User user;

	@OneToMany(mappedBy = "complaint", targetEntity = Comment.class)
	private List<Comment> comments;

	private String location;

	private String landmark;

	private int upvotes;

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Complaint))
			return false;
		Complaint other = (Complaint) obj;
		return Objects.equals(id, other.id);
	}

	public ComplaintTO getComplaintTO() {
		ComplaintTO to = new ComplaintTO();
		to.id = id;
		to.category = category;
		to.description = description;
		List<CommentTO> commentList = new ArrayList<>();

		for (Comment c : comments) {
			commentList.add(c.getCommentTO());
		}

		to.comments = commentList;
		to.landmark = landmark;
		to.location = location;
		System.out.println(comments.size());
		UserTO uTo = new UserTO();
		uTo.setId(user.getId());
		uTo.setFirstName(user.getFirstName());
		uTo.setLastName(user.getLastName());
		to.user = uTo;
		to.status = status;
		to.timestamp = timestamp;
		to.title = title;
		to.upvotes = upvotes;
		return to;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public static class ComplaintTO {
		private Long id;

		private String title;

		private String description;

		private Status status;

		private Category category;

		private Timestamp timestamp;

		private UserTO user;

		private List<CommentTO> comments;

		private String location;
		private String landmark;

		private int upvotes;

		public Long getId() {
			return id;
		}

		public String getTitle() {
			return title;
		}

		public String getDescription() {
			return description;
		}

		public Status getStatus() {
			return status;
		}

		public Category getCategory() {
			return category;
		}

		public Timestamp getTimestamp() {
			return timestamp;
		}

		public UserTO getUser() {
			return user;
		}

		public List<CommentTO> getComments() {
			return comments;
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

		public void setStatus(Status status) {
			this.status = status;
		}

		public void setCategory(Category category) {
			this.category = category;
		}

		public void setTimestamp(Timestamp timestamp) {
			this.timestamp = timestamp;
		}

		public void setUser(UserTO user) {
			this.user = user;
		}

		public void setComments(List<CommentTO> comments) {
			this.comments = comments;
		}

		public String getLocation() {
			return location;
		}

		public String getLandmark() {
			return landmark;
		}

		public int getUpvotes() {
			return upvotes;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public void setLandmark(String landmark) {
			this.landmark = landmark;
		}

		public void setUpvotes(int upvotes) {
			this.upvotes = upvotes;
		}

	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public int getUpvotes() {
		return upvotes;
	}

	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}

}
