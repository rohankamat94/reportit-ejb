package com.cirs.dao.remote;

import java.io.File;
import java.util.List;

import javax.ejb.Remote;

import com.cirs.dao.UserDaoImpl;
import com.cirs.entities.User;

@Remote(UserDaoImpl.class)
public interface UserDao extends Dao<User> {
	public List<User> findAll();
	public void upload(File file);
	
}
