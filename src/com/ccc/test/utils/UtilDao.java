package com.ccc.test.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ccc.test.hibernate.AbSessionHelper;
import com.ccc.test.hibernate.QueryParamsHelper;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserInfo;
import com.sun.faces.facelets.impl.IdMapper;

public class UtilDao{

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
				String nameString = t.getClass().getSimpleName();
				for ( Entry<String, Object> entry : args.entrySet() ){
					qph.add("=", entry.getKey(), entry.getValue());
				}
				String hql = "FROM "+nameString+" WHERE " ;
		        Query query = qph.buildQuery(s, hql);
//		         Query query2 = s.createSQLQuery("");
		        List<T> results = query.list();
				return results;
			}
		}.getResult();		
	}
	/**
	 * @author cxl 
	 * 添加一个实体list，并返回实体list对应的id_List
	 * @param ts  实体list
	 * @return
	 * @throws Exception
	 */
	public static<T>  Serializable addAll(final List<T> ts) throws Exception
	{
		// TODO Auto-generated method stub
		final ArrayList<Integer> id_List = new ArrayList<Integer>();
				return new AbSessionHelper<Serializable>() {
					@Override
					public Serializable handleSession(Session s) {
						 for(T t:ts)
						 {
							int flag = (Integer) s.save(t);
							id_List.add(flag);
						 }
						 return id_List;
					}
				}.getResult();
	}
	public static<T> Serializable add(final T t) throws Exception
	{
		return new AbSessionHelper<Serializable>() {
			@Override
			public Serializable handleSession(Session s) {
				return s.save(t);
			}
		}.getResult();
	}
	/**
	 * @author cxl
	 * 更新对象，判断对象是否为空等业务在外部实现。
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public static<T> boolean update(final T t)  throws Exception{
		if ( t == null )return false;
		return new AbSessionHelper<Boolean>() {
			@Override
			public Boolean handleSession(Session s) {
				 s.saveOrUpdate(t);
				 return true;
			}
		}.getResult();
	}
	
}
