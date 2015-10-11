package com.cirs.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.cirs.dao.remote.UserDao;
import com.cirs.entities.UploadError;
import com.cirs.entities.User;
import com.cirs.entities.UserUploadResponse;
import com.cirs.exceptions.EntityNotCreatedException;

@Stateless(name = "userDao")
public class UserDaoImpl extends AbstractDao<User>implements UserDao {
	// private EntityManager em;

	public UserDaoImpl() {
		super(User.class);
	}

	@PreDestroy
	public void close() {
		closeFactory();
	};

	public UserUploadResponse upload(File f) {
		int entitiesCreated = 0;
		List<UploadError> errors = new ArrayList<>();
		try (Workbook wb = WorkbookFactory.create(f)) {
			Sheet sheet = wb.getSheetAt(0);
			int rowNumber = 2;
			Iterator<Row> i = sheet.rowIterator();
			i.next();
			while (i.hasNext()) {
				Row r = i.next();
				if (r.getCell(0) == null) {
					break;
				}
				String userName = r.getCell(0).getStringCellValue();
				String password = r.getCell(1).getStringCellValue();
				if (userName == null || userName.isEmpty()) {
					errors.add(new UploadError(rowNumber++, "Username cannot be null"));
					continue;
				}
				if (password == null || password.isEmpty()) {
					errors.add(new UploadError(rowNumber++, "Password cannot be null"));
					continue;
				}
				try {
					User u = new User();
					u.setUserName(userName);
					u.setPassword(password);
					boolean created = create(u);
					if (!created) {
						errors.add(new UploadError(rowNumber++, "User with username " + userName + " already exists"));
						continue;
					}
					entitiesCreated++;
				} catch (EntityNotCreatedException e) {
					System.out.println("Exception in upload: " + e.getMessage());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e1) {
			e1.printStackTrace();
		}
		UserUploadResponse ur = new UserUploadResponse(entitiesCreated, errors);
		return ur;
	}

	@Override
	public void create(List<User> entities) {
		EntityManager em = getEntityManager();

		for (User user : entities) {
			em.getTransaction().begin();
			em.persist(user);
			em.flush();
			em.getTransaction().commit();
		}

		em.close();
		closeFactory();
	}

	public User findUserByUserName(String userName) {
		EntityManager em = getEntityManager();
		TypedQuery<User> q = em.createNamedQuery("findUserByUserName", User.class);
		q.setParameter("userName", userName);

		List<User> result = q.getResultList();
		if (result.size() == 0) {
			return null;
		} else {
			return result.get(0);
		}
	}

	@Override
	public boolean create(User entity) throws EntityNotCreatedException {
		User u = findUserByUserName(entity.getUserName());
		if (u == null) {
			return super.create(entity);
		} else {
			return false;
		}
	}

	@Override
	public User findById(Object id) {
		EntityManager em = getEntityManager();
		try {
			Long userId = Long.valueOf(id.toString());
			System.out.println("value of id in user.findById" + id);
			return em.find(User.class, userId);
		} finally {
			em.close();
			closeFactory();
		}
	}

}
