package com.cirs.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import com.cirs.dao.remote.CategoryDao;
import com.cirs.entities.Category;

@Stateless(name = "categoryDao")
public class CategoryDaoImpl extends AbstractDao<Category> implements CategoryDao {
	public CategoryDaoImpl() {
		super(Category.class);
	}

	public List<Category> findAllActive() {
		EntityManager em = getEntityManager();
		List<Category> result = em.createNamedQuery("findActiveCategories", Category.class).getResultList();
		em.close();
		closeFactory();
		return result;
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
