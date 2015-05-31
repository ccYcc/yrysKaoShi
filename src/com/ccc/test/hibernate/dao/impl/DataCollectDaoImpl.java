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
import com.ccc.test.pojo.DataCollectInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.utils.ListUtil;

public class DataCollectDaoImpl implements IBaseHibernateDao<DataCollectInfo> {

	@Override
	public DataCollectInfo getById(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DataCollectInfo> getList(final Map<String, Object> args)
			throws Exception {
		if ( ListUtil.isEmpty(args))return null;
		return new AbSessionHelper<List<DataCollectInfo>>() {
			@Override
			public List<DataCollectInfo> handleSession(Session s) {
				QueryParamsHelper qph = new QueryParamsHelper();
				for ( Entry<String, Object> entry : args.entrySet() ){
					qph.add("=", entry.getKey(), entry.getValue());
				}
				String hql = "FROM DataCollectInfo WHERE " ;    
				
		         Query query = qph.buildQuery(s, hql);
		         List<DataCollectInfo> results = query.list();
				return results;
			}
		}.getResult();
	}

	@Override
	public boolean delete(DataCollectInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(List<DataCollectInfo> list) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(final DataCollectInfo t) throws Exception {
		new AbSessionHelper<Serializable>() {
			@Override
			public Serializable handleSession(Session s) {
				s.update(t);
				return t.getId();
			}
		}.getResult();
		return true;
	}

	@Override
	public Serializable add(final DataCollectInfo t) throws Exception {
		return new AbSessionHelper<Serializable>() {
			@Override
			public Serializable handleSession(Session s) {
				return s.save(t);
			}
		}.getResult();
	}

	@Override
	public Serializable add(List<DataCollectInfo> ts) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
