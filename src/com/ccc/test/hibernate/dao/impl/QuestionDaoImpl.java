package com.ccc.test.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.ccc.test.hibernate.AbSessionHelper;
import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserInfo;

public class QuestionDaoImpl implements IBaseHibernateDao<QuestionInfo> {

	@Override
	public QuestionInfo getById(final Serializable id) throws Exception {
		return new AbSessionHelper<QuestionInfo>() {
			@Override
			public QuestionInfo handleSession(Session s) {
				return (QuestionInfo) s.get(QuestionInfo.class, id);
			}
		}.getResult();
	}

	@Override
	public List<QuestionInfo> getList(Map<String, Object> args)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(QuestionInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(List<QuestionInfo> list) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(QuestionInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable add(final QuestionInfo t) throws Exception {
		return new AbSessionHelper<Serializable>() {
			@Override
			public Serializable handleSession(Session s) {
				return s.save(t);
			}
		}.getResult();
	}

}
