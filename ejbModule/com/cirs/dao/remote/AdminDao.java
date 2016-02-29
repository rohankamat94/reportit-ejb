package com.cirs.dao.remote;

import com.cirs.entities.Admin;

public interface AdminDao extends Dao<Admin>{
	public Admin verifyAdmin(String username, String password);
	public Admin findByUsername(String username);
}
