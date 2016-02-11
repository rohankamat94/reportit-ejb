package com.cirs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
@NamedQueries({
		@NamedQuery(name = Admin.GET_ADMIN, query = "Select a from Admin a where :username=a.username AND :password=a.password"),
		@NamedQuery(name = Admin.FIND_BY_USERNAME, query = "SELECT a from Admin a WHERE a.username=:username") })
public class Admin extends CirsEntity {
	public static final String GET_ADMIN = "getAdmin";
	public static final String FIND_BY_USERNAME = "findByUsername";
	public static final String USERNAME_PARAM = "username";
	public static final String PASSWORD_PARAM = "password";
	@Id
	@Column(name = "admin_id")
	@SequenceGenerator(name = "admin_seq", sequenceName = "admin_admin_id_seq")
	@GeneratedValue(generator = "admin_seq")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	public static class AdminTO {
		private Long id;

		public AdminTO(Long id) {
			this.id = id;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2449732658253299908L;

	@Override
	public Long getId() {
		return id;
	}

}
