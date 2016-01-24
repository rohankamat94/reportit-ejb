package com.cirs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.exceptions.DatabaseException;

import com.cirs.dao.remote.Dao;
import com.cirs.entities.CirsEntity;
import com.cirs.entities.User;
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
			List<T> list = em.createQuery(cq.select(cq.from(entityClass))).getResultList();
			return list;
		} finally {
			em.close();
			closeFactory();
		}
	}

	public boolean delete(Object id) {
		EntityManager em = getEntityManager();
		T t = em.find(entityClass, id);
		try {
			if (t == null) {
				System.out.println(entityClass.getSimpleName() + " with id " + id + " does not exist ");
				return true;
			}
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
	public Long countAllLazy(Map<String, Object> filters) {
		EntityManager em = getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			Root<T> root = cq.from(entityClass);

			List<Predicate> predicates = new ArrayList<>();
			if (filters != null) {
				for (String key : filters.keySet()) {
					System.out.println("calling from count");
					predicates.addAll(getPredicates(cb, root, key, filters.get(key)));
				}
			}

			cq.distinct(true);
			cq.select(cb.count(root));
			cq.where(predicates.toArray(new Predicate[0]));
			return em.createQuery(cq).getSingleResult();
		} finally {
			em.close();
			closeFactory();
		}
	}

	@Override
	public List<T> findAllLazy(int first, int pageSize, Map<String, Object> filters, Map<String, Object> sortParams) {
		EntityManager em = getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(entityClass);
			Root<T> root = (cq.from(entityClass));
			List<Predicate> predicates = new ArrayList<>();
			if (filters != null) {
				for (String key : filters.keySet()) {
					System.out.println("calling from count");
					predicates.addAll(getPredicates(cb, root, key, filters.get(key)));
				}
			}
			List<Order> orderList = new ArrayList<>();
			if (sortParams != null) {
				for (String s : sortParams.keySet()) {
					Boolean b = (Boolean) sortParams.get(s);
					Order o;
					if (b) {
						o = cb.asc(root.get(s));
					} else {
						o = cb.desc(root.get(s));
					}
					orderList.add(o);
				}
			}
			List<T> resultList = em.createQuery(cb.createQuery(entityClass).select(root)
					.where(predicates.toArray(new Predicate[0])).orderBy(orderList)).setFirstResult(first)
					.setMaxResults(pageSize).getResultList();
			System.out.println(resultList.size());
			return resultList == null ? new ArrayList<T>() : resultList;
		} finally {
			em.close();
			closeFactory();
		}
	}

	private List<Predicate> getPredicates(CriteriaBuilder cb, Root<?> root, String key, Object o) {
		List<Predicate> predicates = new ArrayList<>();
		Path<?> path = root.get(key);
		System.out.println("key: " + key + " object: " + o + " object class" + o.getClass().getSimpleName());
		if (path.getJavaType().equals(String.class)) {
			System.out.println("in if");
			Predicate p = cb.like(root.<String> get(key), "%" + o + "%");
			predicates.add(p);
		}
		return predicates;
	}

	@Override
	public boolean create(T entity) throws EntityNotCreatedException {
		EntityManager em = getEntityManager();
		System.out.println("in super create");
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
			em.getTransaction().rollback();
			throw e1;
		} catch (Exception e) {
			System.out.println("in catch 2");
			System.out.println("actual cause " + e.getCause());
			EntityNotCreatedException e1 = new EntityNotCreatedException(
					"could not create " + entityClass.getSimpleName() + e.getMessage());
			em.getTransaction().rollback();
			throw e1;
		} finally {
			em.close();
			closeFactory();
		}
	}

	@Override
	public T findById(Object id) {
		EntityManager em = getEntityManager();
		try {
			if (entityClass.getDeclaredField("id").getType().equals(Long.class) && (id instanceof String)) {
				id = Long.valueOf(id.toString());
			}
			return em.find(entityClass, id);
		} catch (NoSuchFieldException | SecurityException e) {
			System.out.println("in findById reflection exception");
			e.printStackTrace();
			throw new RuntimeException("id " + id + " is not a field in " + entityClass.getSimpleName());
		} finally {
			em.close();
			closeFactory();
		}
	}

}
