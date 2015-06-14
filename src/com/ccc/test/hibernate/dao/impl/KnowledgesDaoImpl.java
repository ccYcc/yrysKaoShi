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
import com.ccc.test.hibernate.dao.interfaces.IknowledgeDao;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.ListUtil;

public class KnowledgesDaoImpl implements IknowledgeDao{

	
	@Override
	public KnowledgeInfo getById(final Serializable id) throws Exception {
		return new AbSessionHelper<KnowledgeInfo>() {
			@Override
			public KnowledgeInfo handleSession(Session s) {
				return (KnowledgeInfo) s.get(KnowledgeInfo.class, id);
			}
		}.getResult();
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
				String hql = "FROM KnowledgeInfo WHERE" ;    
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
	public boolean update(final KnowledgeInfo t) throws Exception {
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
	public Serializable add(final KnowledgeInfo t) throws Exception {
		Map<String, Object> args=new HashMap<String, Object>();
		args.put("name", t.getName());
		Bog.print("add"+t.getName());
		List<KnowledgeInfo>list = getList(args);
		if(list.size()>0)
		{
			t.setId(list.get(0).getId());
			return null;
		}
		return new AbSessionHelper<Serializable>() {
			@Override
			public Serializable handleSession(Session s) {
				return s.save(t);
			}
		}.getResult();
	}

	@Override
	public List<KnowledgeInfo> getChild(final Integer id) throws Exception {
		
//		Map<String,Object> args_map=new HashMap<String, Object>();
//		args_map.put("pid", id);
//		List<KnowledgeRelationInfo> res_list = knowledgeRelationDao.getList(args_map);
//		if(res_list==null)return null;
//		String sss=res_list.get(0).getChildrenIds();
//		for(int i=1;i<res_list.size();i++)
//			sss+=","+res_list.get(i).getChildrenIds();
//		final String child = sss;
//		return new AbSessionHelper<List<KnowledgeInfo>>() {
//			@Override
//			public List<KnowledgeInfo> handleSession(Session s) {
//				String[] temp=child.split(",");
//				String temps="('"+temp[0];
//				for(int i=1;i<temp.length;i++)
//					temps+="','"+temp[i];
//				Bog.print(temps);
//				temps=temps+"')";
//				String hql = "FROM KnowledgeInfo k WHERE k.id in"+temps ;    
//				Bog.print(hql);
//				Query query= s.createQuery(hql);
//		        List<KnowledgeInfo> results = query.list();
//				return results;
//			}
//		}.getResult();
		return new AbSessionHelper<List<KnowledgeInfo>>() {
			@Override
			public List<KnowledgeInfo> handleSession(Session s) {
				String hql = "FROM KnowledgeInfo k WHERE k.pid="+id ;    
				Bog.print(hql);
				Query query= s.createQuery(hql);
		        List<KnowledgeInfo> results = query.list();
				return results;
			}
		}.getResult();
	}

	public Serializable add(List<KnowledgeInfo> ts) {
		// TODO Auto-generated method stub
		return null;
	}
}
