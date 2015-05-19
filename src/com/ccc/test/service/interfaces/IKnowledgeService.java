package com.ccc.test.service.interfaces;

import java.io.Serializable;


/**
 *   获取知识点接口
 *
 */
public interface IKnowledgeService {

	public Serializable getKnowlegeByID(int id) throws Exception;
	
}
