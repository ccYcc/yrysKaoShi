package com.ccc.test.service.interfaces;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 *   获取知识点接口
 *
 */
public interface IKnowledgeService {

	public static final String category="resources/knowledge";
	/**
	 * 上传知识点文件中模板位置参数
	 */
	public static final String knowledge="knowledge";
	public static final String ARG_desc="desc";
	public static final String ARG_parent_knowledge="parent_knowledge";
	public static final int knowledge_index=0;
	public static final int parent_knowledge_index=2;
	public static final int desc_index=1;
	/**
	 *  id > 0 获取孩子节点
	 * id=null 获取root节点
	 */
	public Serializable getKnowlegeByID(Integer id) throws Exception;
	
	/**
	 *  上传知识点
	 * id=null 获取root节点
	 */
	public Serializable uploadKnowledge(HttpServletRequest request) throws Exception;
}
