package com.cirs.dao.remote;

import java.util.List;

import com.cirs.entities.Complaint;
import com.cirs.entities.Complaint.ComplaintTO;

public interface ComplaintDao extends Dao<Complaint> {
	List<ComplaintTO> getComplaintwithComments(Long adminId);

	Complaint findByIdWithComments(Long id, Long adminId);
}
