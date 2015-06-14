package com.ccc.test.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.cache.util.setCache;

import com.ccc.test.hibernate.AbSessionHelper;
import com.ccc.test.hibernate.QueryParamsHelper;
import com.ccc.test.pojo.KnowledgeQuestionRelationInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserInfo;

public class UtilDao{

	/**鏍规嵁id鑾峰彇瀵硅薄
	 * @param t
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static<T> T getById(final T t,final Serializable id) throws Exception{
		if ( id == null )return null;
		return new AbSessionHelper<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T handleSession(Session s) {
				return (T) s.get(t.getClass(), id);
			}
		}.getResult();
	}
/**
 * 多条件查询
 * @param t
 * @param args
 * @return
 * @throws Exception
 */
	public static<T> List<T> getList(final T t,final Map<String, Object> args) throws Exception{
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
		        @SuppressWarnings("unchecked")
				List<T> results = query.list();
				return results;
			}
		}.getResult();		
	}
	
	public static <T> Boolean Delete(final T t,final Map<String, Object> args) throws Exception{
		if ( ListUtil.isEmpty(args))return false;
		return new AbSessionHelper<Boolean>() {
			@Override
			public Boolean handleSession(Session s) {
				QueryParamsHelper qph = new QueryParamsHelper();
				String nameString = t.getClass().getSimpleName();
				for ( Entry<String, Object> entry : args.entrySet() ){
					qph.add("=", entry.getKey(), entry.getValue());
				}
				String hql = "FROM "+nameString+" WHERE " ;
		        Query query = qph.buildQuery(s, hql);
		        @SuppressWarnings("unchecked")
				List<T> list = query.list();
		       if(ListUtil.isEmpty(list))return false;
		        for(T t:list)
		        	s.delete(t);
				return true;
			}
		}.getResult();		
	}
	/**
	 * @author cxl 
	 * 娣诲姞涓€涓疄浣搇ist锛屽苟杩斿洖瀹炰綋list瀵瑰簲鐨刬d_List
	 * @param ts 瀹炰綋list
	 * @return
	 * @throws Exception
	 */
	public static<T>  Serializable addAll(final List<T> ts) throws Exception
	{
		// TODO Auto-generated method stub
		final ArrayList<Integer> id_List = new ArrayList<Integer>();
		if(ListUtil.isEmpty(ts))return null;
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
	 * 鏇存柊瀵硅薄锛屽垽鏂璞℃槸鍚︿负绌虹瓑涓氬姟鍦ㄥ閮ㄥ疄鐜般€?
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
	
	public static<T> List<T> getBySql(final T t, final String sql) throws Exception {
		if(StringUtils.isBlank(sql))return null;
		return new AbSessionHelper<List<T>>() {
			@Override
			public List<T> handleSession(Session s) {
				String nameString = t.getClass().getSimpleName();
				String  hql=sql;
				Query query= s.createQuery(hql);
		        List<T> results = query.list();
				return results;
			}
		}.getResult();
	}
	
	public static<T> void DeleteByArgs(final T t, final Map<String,Object>args) throws Exception {
		if(ListUtil.isEmpty(args))return;
		new AbSessionHelper<Boolean>() {
			@Override
			public Boolean handleSession(Session s) {
				
				QueryParamsHelper qph = new QueryParamsHelper();
				for ( Entry<String, Object> entry : args.entrySet() ){
					qph.add("=", entry.getKey(), entry.getValue());
				}
				String nameString = t.getClass().getSimpleName();
				String hql = "Delete FROM "+nameString+" WHERE " ;    
		        Query query = qph.buildQuery(s, hql);
		        query.executeUpdate();
		        return true;
			}
		}.getResult();
	}
	
	/**
	 * @param t
	 * @param ids id串，逗号分隔
	 * @param split
	 * @return 返回t的list
	 */
	public static <T> Serializable useIDStringToList(T t , String ids , String split)
	{
		if(StringUtils.isBlank(ids))return null;
		try {
			List<T> info_list=new ArrayList<T>();
			List<String> ID_list = ListUtil.stringsToListSplitBy(ids, split);
			if(ID_list==null||ID_list.size()<=0)return null;
			for(String Id : ID_list)
			{
				if(StringUtils.isBlank(Id))continue;
				if(!StringUtils.isNumeric(Id)&&!(
						Id.substring(0, 1).equals("-")
						&&StringUtils.isNumeric(Id.substring(1, Id.length()))
						))continue;
				Integer logid = Integer.parseInt(Id);
				T info = UtilDao.getById(t, logid);
				if(info!=null)
					info_list.add(info);
			}
			return (Serializable) info_list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
