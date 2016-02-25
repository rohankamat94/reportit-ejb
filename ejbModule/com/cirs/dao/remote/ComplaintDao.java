package com.cirs.dao.remote;

import java.util.List;

import javax.ejb.Remote;

import com.cirs.dao.impl.ComplaintDaoImpl;
import com.cirs.entities.Complaint;
import com.cirs.entities.Complaint.ComplaintTO;

@Remote(ComplaintDaoImpl.class)
public interface ComplaintDao extends Dao<Complaint> {
	List<ComplaintTO> getComplaintwithComments(Long adminId);

	Complaint findByIdWithComments(Long id, Long adminId);
}
