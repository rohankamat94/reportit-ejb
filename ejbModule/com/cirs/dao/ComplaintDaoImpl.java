package com.cirs.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.cirs.dao.remote.ComplaintDao;
import com.cirs.entities.Complaint;
import com.cirs.entities.Complaint.ComplaintTO;

@Stateless(name = "complaintDao")
public class ComplaintDaoImpl extends AbstractDao<Complaint> implements ComplaintDao {

	public ComplaintDaoImpl() {
		super(Complaint.class);
	}

	@Override
	public List<ComplaintTO> getComplaintwithComments() {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Complaint> cq = cb.createQuery(entityClass);
		try {
			List<Complaint> list = em.createQuery(cq.select(cq.from(entityClass))).getResultList();
			list.stream().forEach(u -> ((Complaint) u).getComments().size());
			List<ComplaintTO> toList = list.stream().map(c -> c.getComplaintTO()).collect(Collectors.toList());
			return toList;
		} finally {
			em.close();
			closeFactory();
		}
	}
}
