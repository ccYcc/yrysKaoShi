package com.ccc.test.service.interfaces;

import java.io.Serializable;

/**
 *   获取知识点接口
 *
 */
public interface IKnowledgeService {

	/**
	 *  id > 0 获取孩子节点
	 * id=null 获取root节点
	 */
	public Serializable getKnowlegeByID(Integer id) throws Exception;
	
}
