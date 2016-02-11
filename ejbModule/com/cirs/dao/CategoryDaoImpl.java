package com.cirs.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.cirs.dao.remote.CategoryDao;
import com.cirs.entities.Category;

@Stateless(name = "categoryDao")
public class CategoryDaoImpl extends AbstractDao<Category> implements CategoryDao {
	public CategoryDaoImpl() {
		super(Category.class);
	}

	@Override
	public List<Category> findAll(Long adminId) {
		EntityManager em = getEntityManager();
		try {
			TypedQuery<Category> query = em.createNamedQuery(Category.FIND_BY_ADMIN, Category.class);
			query.setParameter(Category.PARAM_ADMIN_ID, adminId);
			List<Category> result = query.getResultList();
			return result;
		} finally {
			em.close();
			closeFactory();
		}
	}

	@Override
	public Category findById(Object id) {
		EntityManager em = getEntityManager();
		try {
			Category cat = em.find(Category.class, Long.valueOf(id.toString()));
			// in case id is passed as a String, parse to Long
			return cat;
		} finally {
			em.close();
			closeFactory();
		}
	}

}
