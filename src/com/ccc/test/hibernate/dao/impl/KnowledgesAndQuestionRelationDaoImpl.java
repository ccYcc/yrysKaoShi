package com.ccc.test.hibernate.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ccc.test.hibernate.AbSessionHelper;
import com.ccc.test.hibernate.QueryParamsHelper;
import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.KnowledgeQuestionRelationInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.utils.ListUtil;

public class KnowledgesAndQuestionRelationDaoImpl implements IBaseHibernateDao<KnowledgeQuestionRelationInfo>{

	@Override
	public KnowledgeQuestionRelationInfo getById(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KnowledgeQuestionRelationInfo> getList(final Map<String, Object> args)
			throws Exception {
		return null;
	}

	@Override
	public boolean delete(KnowledgeQuestionRelationInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(List<KnowledgeQuestionRelationInfo> list) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(KnowledgeQuestionRelationInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable add(final KnowledgeQuestionRelationInfo t) throws Exception {
		return new AbSessionHelper<Serializable>() {
			@Override
			public Serializable handleSession(Session s) {
				return s.save(t);
			}
		}.getResult();
	}
}
