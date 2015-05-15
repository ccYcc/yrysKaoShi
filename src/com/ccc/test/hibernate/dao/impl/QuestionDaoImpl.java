package com.ccc.test.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.QuestionInfo;

public class QuestionDaoImpl implements IBaseHibernateDao<QuestionInfo> {

	@Override
	public QuestionInfo getById(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
	public Serializable add(QuestionInfo t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
