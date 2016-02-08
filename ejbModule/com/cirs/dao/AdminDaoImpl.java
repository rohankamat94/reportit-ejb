package com.cirs.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.cirs.dao.remote.AdminDao;
import com.cirs.entities.Admin;

@Stateless(name = "adminDao")
public class AdminDaoImpl extends AbstractDao<Admin> implements AdminDao {
	public AdminDaoImpl() {
		super(Admin.class);
	}

	@Override
	public Admin verifyAdmin(String username, String password) {
		EntityManager em = getEntityManager();
		try {
			TypedQuery<Admin> query= em.createNamedQuery(Admin.GET_ADMIN, Admin.class);
			query.setParameter(Admin.USERNAME_PARAM, username);
			query.setParameter(Admin.PASSWORD_PARAM, password);
			List<Admin> admin=query.getResultList();
			return admin.size()==0?null:admin.get(0);
			
		} finally {
			em.close();
			closeFactory();
		}
	}

	@Override
	public Admin findByUsername(String username) {
		EntityManager em=getEntityManager();
		try{
			TypedQuery<Admin> query= em.createNamedQuery(Admin.FIND_BY_USERNAME, Admin.class);
			query.setParameter(Admin.USERNAME_PARAM, username);
			List<Admin> admin=query.getResultList();
			return admin.size()==0?null:admin.get(0);
			
		}finally{
			em.close();
			closeFactory();
		}
	}

}
