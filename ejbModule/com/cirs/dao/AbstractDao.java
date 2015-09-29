package com.cirs.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.eclipse.persistence.exceptions.DatabaseException;

import com.cirs.dao.remote.Dao;
import com.cirs.entities.CirsEntity;
import com.cirs.exceptions.EntityNotCreatedException;
import com.cirs.exceptions.EntityNotFoundException;

public abstract class AbstractDao<T extends CirsEntity> implements Dao<T> {

	Class<T> entityClass;
	EntityManagerFactory emf;

	public AbstractDao(Class<T> clazz) {
		entityClass = clazz;
	}

	public EntityManager getEntityManager() {
		emf = Persistence.createEntityManagerFactory("cirs");
		return emf.createEntityManager();
	}

	protected void closeFactory() {
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
	}

	public List<T> findAll() {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);
		try {
			return em.createQuery(cq.select(cq.from(entityClass))).getResultList();
		} finally {
			em.close();
			closeFactory();
		}
	}

	public boolean delete(Object id) {
		EntityManager em = getEntityManager();
		T t = em.find(entityClass, id);
		if (t == null) {
			System.out.println(entityClass.getSimpleName() + " with id " + id + " does not exist ");
			return true;
		}
		try {
			em.getTransaction().begin();
			em.remove(t);
			em.flush();
			em.getTransaction().commit();
			return true;
		} catch (DatabaseException e) {
			e.printStackTrace();
			return false;
		} finally {
			em.close();
			closeFactory();
		}
	}

	@Override
	public boolean edit(T entity) throws EntityNotFoundException {
		EntityManager em = getEntityManager();
		if (em.find(entityClass, entity.getId()) == null) {
			throw new EntityNotFoundException(
					"Could not find " + entityClass.getSimpleName() + "with id" + entity.getId());
		}
		try {
			System.out.println("in edit");
			em.getTransaction().begin();
			em.merge(entity);
			em.flush();
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			em.close();
			closeFactory();
		}
	}

	@Override
	public boolean create(T entity) throws EntityNotCreatedException {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.flush();
			em.getTransaction().commit();
			return true;
		} catch (PersistenceException e) {
			EntityNotCreatedException e1 = new EntityNotCreatedException(
					"could not create " + entityClass.getSimpleName() + e.getMessage());
			e1.addSuppressed(e);
			throw e1;
		} catch (Exception e) {
			System.out.println("in catch 2");
			throw e;
		} finally {
			em.close();
			closeFactory();
		}

	}
}
