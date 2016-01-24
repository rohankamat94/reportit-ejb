package com.cirs.dao.remote;

import javax.ejb.Remote;

import com.cirs.dao.ComplaintDaoImpl;
import com.cirs.entities.Complaint;

@Remote(ComplaintDaoImpl.class)
public interface ComplaintDao extends Dao<Complaint> {
	
}
