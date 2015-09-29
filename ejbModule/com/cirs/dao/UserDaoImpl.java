package com.cirs.dao;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.cirs.dao.remote.UserDao;
import com.cirs.entities.Gender;
import com.cirs.entities.User;
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

	public void upload(File f) {
		try (Workbook wb = WorkbookFactory.create(f)) {
			Sheet sheet = wb.getSheetAt(0);
			// int rowNumber = 1;
			Iterator<Row> i = sheet.rowIterator();
			i.next();
			int entitiesCreated = 0;
			while (i.hasNext()) {
				Row r = i.next();
				if (r.getCell(0)==null) {
					break;
				}
				String userName = r.getCell(0).getStringCellValue();
				String password = r.getCell(1).getStringCellValue();
				
				try {
					User u = new User();
					u.setUserName(userName);
					u.setPassword(password);
					System.out.println("before create");
					create(u);
					entitiesCreated++;

				} catch (EntityNotCreatedException e) {
					System.out.println(e);
				}
			}
			System.out.println("no of entities created " + entitiesCreated);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e1) {
			e1.printStackTrace();
		}
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

}
