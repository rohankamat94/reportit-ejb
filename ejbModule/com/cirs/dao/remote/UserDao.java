package com.cirs.dao.remote;

import java.io.File;
import java.util.List;

import com.cirs.entities.Admin;
import com.cirs.entities.User;
import com.cirs.entities.User.UserTO;
import com.cirs.entities.UserUploadResponse;

public interface UserDao extends Dao<User> {

	public UserUploadResponse upload(Admin admin, File file);

	public User findUserByUserName(String userName);

	public UserTO verifyCredentials(String userName, String password);

	public List<UserTO> findAllUsersWithComplaints(Long adminId);
	
	public UserTO findUserWIthComplaint(Long id, Long adminId);
}
