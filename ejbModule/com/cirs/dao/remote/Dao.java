package com.cirs.dao.remote;

import java.util.List;

import com.cirs.entities.CirsEntity;
import com.cirs.exceptions.EntityNotCreatedException;

public interface Dao<T extends CirsEntity> {
	public List<T> findAll();

	public boolean create(T entity) throws EntityNotCreatedException;

	public boolean edit(T entity);
	public boolean delete(Object id);

	public void create(List<T> entites);
}
