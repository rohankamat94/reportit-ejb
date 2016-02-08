package com.cirs.dao.remote;

import javax.ejb.Remote;

import com.cirs.dao.AdminDaoImpl;
import com.cirs.entities.Admin;

@Remote(AdminDaoImpl.class)
public interface AdminDao extends Dao<Admin>{
	public Admin verifyAdmin(String username, String password);
	public Admin findByUsername(String username);
}
