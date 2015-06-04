package com.ccc.test.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.UserAnswerLogInfo;

public class AnswerLogDaoImpl implements IBaseHibernateDao<UserAnswerLogInfo> {

	@Override
	public UserAnswerLogInfo getById(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserAnswerLogInfo> getList(Map<String, Object> args)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(UserAnswerLogInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(List<UserAnswerLogInfo> list) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(UserAnswerLogInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable add(UserAnswerLogInfo t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable add(List<UserAnswerLogInfo> ts) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
