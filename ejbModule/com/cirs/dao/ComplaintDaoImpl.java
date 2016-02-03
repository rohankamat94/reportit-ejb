package com.cirs.dao;

import javax.ejb.Stateless;

import com.cirs.dao.remote.ComplaintDao;
import com.cirs.entities.Complaint;

@Stateless(name = "complaintDao")
public class ComplaintDaoImpl extends AbstractDao<Complaint> implements ComplaintDao {

	public ComplaintDaoImpl() {
		super(Complaint.class);
	}
}
