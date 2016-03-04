package com.cirs.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.cirs.dao.remote.ComplaintDao;
import com.cirs.entities.CirsEntity;
import com.cirs.entities.Complaint;
import com.cirs.entities.Complaint.ComplaintTO;
import com.cirs.entities.Complaint.Status;

@Stateless(name = "complaintDao")
@Remote(ComplaintDao.class)
public class ComplaintDaoImpl extends AbstractDao<Complaint> implements ComplaintDao {

	public ComplaintDaoImpl() {
		super(Complaint.class);
	}

	@Override
	public Complaint findByIdWithComments(Long id, Long adminId) {

		EntityManager em = getEntityManager();
		try {
			Complaint c = em.find(entityClass, id);
			if (!c.getUser().getAdmin().getId().equals(adminId)) {
				return null;
			}
			c.getComments().size();
			return c;
		} catch (SecurityException e) {
			System.out.println("in findById reflection exception");
			e.printStackTrace();
		} finally {
			em.close();
			closeFactory();
		}

		return null;
	}

	@Override
	public List<Complaint> findAll(Long adminId) {
		EntityManager em = getEntityManager();
		try {
			TypedQuery<Complaint> query = em.createNamedQuery(Complaint.FIND_BY_ADMIN, Complaint.class);
			query.setParameter(CirsEntity.PARAM_ADMIN_ID, adminId);
			List<Complaint> result = query.getResultList();
			return result;
		} finally {
			em.close();
			closeFactory();
		}
	}

	@Override
	public List<ComplaintTO> getComplaintwithComments(Long adminId) {
		EntityManager em = getEntityManager();
		try {

			TypedQuery<Complaint> query = em.createNamedQuery(Complaint.FIND_BY_ADMIN, Complaint.class);
			query.setParameter(CirsEntity.PARAM_ADMIN_ID, adminId);
			List<Complaint> list = query.getResultList();
			List<ComplaintTO> users = list.stream().map(Complaint::getComplaintTO).collect(Collectors.toList());
			return users;
		} finally {
			em.close();
			closeFactory();
		}
	}

	@Override
	protected List<Predicate> getPredicates(CriteriaBuilder cb, Path<?> root, String key, Object o) {
		List<Predicate> preds = super.getPredicates(cb, root, key, o);
		System.out.println("complaint dao get predicates -key:" + key + " object:" + o);
		Path<?> path = root.get(key);

		if (key.equals("status")) {
			Predicate p=Arrays.stream(Status.values()).filter(s -> s.toString().contains(o.toString().toUpperCase()))
					.peek(System.out::println).map(t -> cb.equal(path, t)).reduce(cb.or(), cb::or);
			preds.add(p);
		}
		return preds;
	}
}
