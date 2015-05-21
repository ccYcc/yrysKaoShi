package com.ccc.test.hibernate.dao.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserInfo;

public interface IknowledgeDao extends IBaseHibernateDao<KnowledgeInfo> {
	
//	public KnowledgeInfo getByName(final String Name) throws Exception;
	
//	public abstract T getById(Serializable id)  throws Exception ;
//	public abstract List<T> getList(Map<String, Object> args)  throws Exception;
//	public abstract boolean delete(T t)  throws Exception;
//	public abstract boolean deleteAll(List<T> list)  throws Exception;
//	public abstract boolean update(T t)  throws Exception;
//	public abstract Serializable add(T t)  throws Exception;
	
	public List<KnowledgeInfo> getChild(final Integer id) throws Exception;
}
