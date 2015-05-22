package com.ccc.test.hibernate.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.ccc.test.pojo.QuestionInfo;

public interface IQuestionDao extends IBaseHibernateDao<QuestionInfo> {

	/**
	 * @param id 根据id获取对象
	 * @param args 查询参数
	 * @return
	 */
	public abstract List<QuestionInfo> getQuestionByRandom(int size,Map<String, Object> args)  throws Exception ;
}
