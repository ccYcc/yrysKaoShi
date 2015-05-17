package com.ccc.test.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ccc.test.hibernate.AbSessionHelper;
import com.ccc.test.hibernate.QueryParamsHelper;
import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.utils.ListUtil;

public class KnowledgesDaoImpl implements IBaseHibernateDao<KnowledgeInfo>{

	@Override
	public KnowledgeInfo getById(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KnowledgeInfo> getList(final Map<String, Object> args)
			throws Exception {
		if ( ListUtil.isEmpty(args))return null;
		return new AbSessionHelper<List<KnowledgeInfo>>() {
			@Override
			public List<KnowledgeInfo> handleSession(Session s) {
				QueryParamsHelper qph = new QueryParamsHelper();
				for ( Entry<String, Object> entry : args.entrySet() ){
					qph.add("=", entry.getKey(), entry.getValue());
				}
				String hql = "FROM tb_knowledge_node WHERE " ;    
				
		         Query query = qph.buildQuery(s, hql);
		         List<KnowledgeInfo> results = query.list();
				return results;
			}
		}.getResult();
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
