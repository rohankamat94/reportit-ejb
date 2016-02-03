package com.cirs.entities;

import java.io.Serializable;

public abstract class CirsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8673211674341347638L;

	public abstract Object getId();
}
