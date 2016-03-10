package com.cirs.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.cirs.dao.remote.UpvoteDao;
import com.cirs.entities.Complaint;
import com.cirs.entities.Complaint.ComplaintTO;
import com.cirs.entities.Upvote;
import com.cirs.entities.User;
import com.cirs.exceptions.EntityAlreadyExistsException;

@Stateless(name = "upvoteDao")
@Remote(UpvoteDao.class)
public class UpvoteDaoImpl implements UpvoteDao {

	EntityManagerFactory emf;

	@Override
	public void createUpvote(Upvote upvote) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(upvote);
			em.flush();
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new EntityAlreadyExistsException();
		} finally {
			em.close();
			closeEMF();
		}
	}

	private void closeEMF() {
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
	}

	@Override
	public List<Upvote> getAllUpvotes() {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Upvote> cq = cb.createQuery(Upvote.class);
			cq.select(cq.from(Upvote.class));
			return em.createQuery(cq).getResultList();
		} finally {
			em.close();
			closeEMF();
		}
	}

	@Override
	public List<ComplaintTO> getAllUpvotedComplaintsByUser(User user) {
		Long userId = user.getId();
		List<Upvote> upvotes = getAllUpvotes();
		return upvotes.stream().filter(u -> u.getUser().getId().equals(userId)).map(Upvote::getComplaint)
				.map(Complaint::getComplaintTO).collect(Collectors.toList());
	}

	// @Override
	public void createUpvotes(User user, Complaint... complaints) {

	}

	private EntityManager getEntityManager() {
		emf = Persistence.createEntityManagerFactory("cirs");
		return emf.createEntityManager();
	}
}
