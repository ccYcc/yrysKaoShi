package com.ccc.test.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.KnowledgeInfo;

public class KnowledgesDaoImpl implements IBaseHibernateDao<KnowledgeInfo>{

	@Override
	public KnowledgeInfo getById(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KnowledgeInfo> getList(Map<String, Object> args)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(KnowledgeInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(List<KnowledgeInfo> list) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(KnowledgeInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable add(KnowledgeInfo t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
