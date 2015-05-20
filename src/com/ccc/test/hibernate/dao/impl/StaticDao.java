package com.ccc.test.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ccc.test.hibernate.AbSessionHelper;
import com.ccc.test.hibernate.QueryParamsHelper;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.utils.ListUtil;

public class StaticDao{

	/**根据id获取对象
	 * @param t
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static<T> T getById(final Class<T> t,final Serializable id) throws Exception{
		return new AbSessionHelper<T>() {
			@Override
			public T handleSession(Session s) {
				return (T) s.get(t, id);
			}
		}.getResult();
	}

	public static<T> List<T> getList(final Class<T> t,final Map<String, Object> args) throws Exception{
		if ( ListUtil.isEmpty(args))return null;
		return new AbSessionHelper<List<T>>() {
			@Override
			public List<T> handleSession(Session s) {
				QueryParamsHelper qph = new QueryParamsHelper();
				for ( Entry<String, Object> entry : args.entrySet() ){
					qph.add("=", entry.getKey(), entry.getValue());
				}
				String hql = "FROM UserInfo WHERE " ;    
		         Query query = qph.buildQuery(s, hql);
		         List<T> results = query.list();
				return results;
			}
		}.getResult();		
	}
}
