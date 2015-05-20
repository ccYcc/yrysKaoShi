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
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.utils.ListUtil;
import com.sun.istack.internal.FinalArrayList;

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
	public List<QuestionInfo> getList(final Map<String, Object> args)
			throws Exception {
		if ( ListUtil.isEmpty(args))return null;
		return new AbSessionHelper<List<QuestionInfo>>() {
			@Override
			public List<QuestionInfo> handleSession(Session s) {
				QueryParamsHelper qph = new QueryParamsHelper();
				for ( Entry<String, Object> entry : args.entrySet() ){
					qph.add("=", entry.getKey(), entry.getValue());
				}
				String hql = "FROM UserInfo WHERE " ;    
				
		         Query query = qph.buildQuery(s, hql);
		         List<QuestionInfo> results = query.list();
				return results;
			}
		}.getResult();
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

	@Override
	public Serializable add(final List<QuestionInfo> ts) throws Exception {
		// TODO Auto-generated method stub
		return new AbSessionHelper<Serializable>() {
			@Override
			public Serializable handleSession(Session s) {
				 for(QuestionInfo questionInfo:ts)
				 {
					 int flag = (Integer) s.save(questionInfo);
					 if(flag>0)
					 {
						 questionInfo.setId(flag);
					 }
				 }
				 return true;
			}
		}.getResult();
	}

}
