package com.cirs.dao.remote;

import java.util.List;

import javax.ejb.Remote;

import com.cirs.dao.CategoryDaoImpl;
import com.cirs.entities.Category;

@Remote(CategoryDaoImpl.class)
public interface CategoryDao extends Dao<Category> {
	List<Category> findAllActive(Long adminId);

}
