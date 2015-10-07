package com.cirs.dao.remote;

import java.util.List;
import java.util.Map;

import com.cirs.entities.CirsEntity;
import com.cirs.exceptions.EntityNotCreatedException;
import com.cirs.exceptions.EntityNotFoundException;

public interface Dao<T extends CirsEntity> {
	public List<T> findAll();

	public boolean create(T entity) throws EntityNotCreatedException;

	public boolean edit(T entity) throws EntityNotFoundException;

	public boolean delete(Object id);

	public Long countAllLazy(Map<String,Object> filters);

	public List<T> findAllLazy(int first, int pageSize, Map<String, Object> filters, Map<String, Object> sortMap);

	public void create(List<T> entites);
}
