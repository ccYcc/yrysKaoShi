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
import com.ccc.test.pojo.KnowledgeRelationInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.utils.ListUtil;

public class KnowledgesRelationDaoImpl implements IBaseHibernateDao<KnowledgeRelationInfo>{

	@Override
	public KnowledgeRelationInfo getById(final Serializable id) throws Exception {
		return new AbSessionHelper<KnowledgeRelationInfo>() {
			@Override
			public KnowledgeRelationInfo handleSession(Session s) {
				return (KnowledgeRelationInfo) s.get(KnowledgeRelationInfo.class, id);
			}
		}.getResult();
	}

	@Override
	public List<KnowledgeRelationInfo> getList(final Map<String, Object> args)
			throws Exception {
		if ( ListUtil.isEmpty(args))return null;
		return new AbSessionHelper<List<KnowledgeRelationInfo>>() {
			@Override
			public List<KnowledgeRelationInfo> handleSession(Session s) {
				QueryParamsHelper qph = new QueryParamsHelper();
				for ( Entry<String, Object> entry : args.entrySet() ){
					qph.add("=", entry.getKey(), entry.getValue());
				}
				String hql = "FROM KnowledgeRelationInfo WHERE " ;    
		         Query query = qph.buildQuery(s, hql);
		         List<KnowledgeRelationInfo> results = query.list();
				return results;
			}
		}.getResult();
	}

	@Override
	public boolean delete(KnowledgeRelationInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(List<KnowledgeRelationInfo> list) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(KnowledgeRelationInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable add(final KnowledgeRelationInfo t) throws Exception {
		return new AbSessionHelper<Serializable>() {
			@Override
			public Serializable handleSession(Session s) {
				return s.save(t);
			}
		}.getResult();
	}
}
