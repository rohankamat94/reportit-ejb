package com.cirs.dao.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.cirs.dao.remote.CommentDao;
import com.cirs.entities.Comment;

@Stateless(name = "commentDao")
@Remote(CommentDao.class)
public class CommentDaoImpl extends AbstractDao<Comment> {
	public CommentDaoImpl() {
		super(Comment.class);
	}

	@Override
	public List<Comment> findAll(Long adminId) {
		throw new UnsupportedOperationException();
	}

}
