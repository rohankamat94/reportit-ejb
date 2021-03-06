package com.cirs.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.cirs.entities.Admin.AdminTO;
import com.cirs.entities.Comment.CommentTO;
import com.cirs.entities.Complaint.ComplaintTO;

@Entity
@Table(name = "cirs_user")
@NamedQueries({ @NamedQuery(name = "findUserByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
		@NamedQuery(name = "verifyCredentials", query = "SELECT u from User u WHERE u.userName= :userName and u.password= :password"),
		@NamedQuery(name = User.FIND_BY_ADMIN, query = "SELECT u from User u WHERE u.admin.id=:adminId") })
public class User extends CirsEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6400091099179867101L;
	public static final String FIND_BY_ADMIN = "findUserByAdmin";

	@Id
	@SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_id_seq")
	@Column(updatable = false)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "username", nullable = false, updatable = false, unique = true)
	private String userName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(name = "password", nullable = false, updatable = false)
	private String password;

	@Column(name = "profile_pic")
	private String profilePic;

	@Column(name = "dob")
	private Date dob;

	@Column(name = "phone_number")
	private String phone;

	@Column(name = "gcm_token")
	private String gcmToken;

	@JoinColumn(referencedColumnName = "admin_id", name = "admin_id", nullable = false, updatable = false)
	@ManyToOne
	private Admin admin;

	@OneToMany(mappedBy = "user", targetEntity = Complaint.class)
	private List<Complaint> complaints;

	public List<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(List<Complaint> complaints) {
		this.complaints = complaints;
	}

	public Long getId() {
		return id;
	}

	public User() {
	}

	public User(String firstName, String email, String userName, String lastName, Date dob, Gender gender,
			String password, String profilePic, String phone) {
		super();
		this.dob = dob;
		this.firstName = firstName;
		this.email = email;
		this.userName = userName;
		this.lastName = lastName;
		this.gender = gender;
		this.password = password;
		this.profilePic = profilePic;
		this.phone = phone;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public UserTO getUserTO() {
		UserTO to = new UserTO();
		to.id = id;
		to.firstName = firstName;
		to.admin = new AdminTO(admin.getId());
		to.dob = dob;
		to.lastName = lastName;
		to.gender = gender;
		to.phone = phone;
		to.userName = userName;
		to.email = email;
		List<ComplaintTO> complaintTos = new ArrayList<>();
		for (Complaint c : complaints) {
			complaintTos.add(c.getComplaintTO());
		}
		to.complaints = complaintTos;
		return to;
	}

	public static class UserTO {
		private Long id;

		private String firstName;

		private String email;

		private String userName;

		private String lastName;

		private Gender gender;

		private String profilePic;

		private Date dob;

		private String phone;

		private AdminTO admin;

		private List<ComplaintTO> complaints;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getEmail() {
			return email;
		}

		public String getUserName() {
			return userName;
		}

		public String getLastName() {
			return lastName;
		}

		public Gender getGender() {
			return gender;
		}

		public String getProfilePic() {
			return profilePic;
		}

		public Date getDob() {
			return dob;
		}

		public String getPhone() {
			return phone;
		}

		public AdminTO getAdmin() {
			return admin;
		}

		public List<ComplaintTO> getComplaints() {
			return complaints;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public void setGender(Gender gender) {
			this.gender = gender;
		}

		public void setProfilePic(String profilePic) {
			this.profilePic = profilePic;
		}

		public void setDob(Date dob) {
			this.dob = dob;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public void setAdmin(AdminTO admin) {
			this.admin = admin;
		}

		public void setComplaints(List<ComplaintTO> complaints) {
			this.complaints = complaints;
		}

	}

	public String getGcmToken() {
		return gcmToken;
	}

	public void setGcmToken(String gcmToken) {
		this.gcmToken = gcmToken;
	}
}
