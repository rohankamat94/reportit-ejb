package com.cirs.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "category")
@NamedQueries(@NamedQuery(name = Category.FIND_BY_ADMIN, query = "SELECT c FROM Category c WHERE c.admin.id=:adminId"))
public class Category extends CirsEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4008581528843761504L;

	public static final String FIND_BY_ADMIN = "findCategoryByAdmin";

	@Id
	@SequenceGenerator(name = "category_id_seq", sequenceName = "category_category_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "category_id_seq")
	@Column(name = "category_id")
	private Long id;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "active")
	private Boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@ManyToOne(optional = false)
	@JoinColumn(referencedColumnName = "admin_id", name = "admin_id")
	private Admin admin;

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
		if (!(obj instanceof Category))
			return false;
		Category other = (Category) obj;
		return Objects.equals(id, other.id);
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
