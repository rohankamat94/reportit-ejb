package com.cirs.entities;

import java.io.Serializable;

public abstract class CirsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8673211674341347638L;
	public static final String PARAM_ADMIN_ID = "adminId";

	public abstract Object getId();
}
