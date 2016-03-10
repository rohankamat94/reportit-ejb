package com.cirs.dao.remote;

import java.util.List;

import com.cirs.entities.Complaint.ComplaintTO;
import com.cirs.entities.Upvote;
import com.cirs.entities.User;

public interface UpvoteDao {
	public void createUpvote(Upvote upvote);

	// public void createUpvotes(User user, Complaint... complaints);
	public List<Upvote> getAllUpvotes();

	public List<ComplaintTO> getAllUpvotedComplaintsByUser(User user);
}
