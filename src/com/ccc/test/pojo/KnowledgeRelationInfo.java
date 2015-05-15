package com.ccc.test.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**知识点关系类
 * @author Trible Chen
 *
 */
@Entity
@Table(name = KnowledgeRelationInfo.TABLE_NAME)
public class KnowledgeRelationInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "tb_knowledge_relation";
	public static final String COLUMN_NODEID = "nid";
	public static final String COLUMN_PARENTID = "pid";
	public static final String COLUMN_CHILDREN_IDS = "cids";
	
	/**
	 * 知识点id
	 */
	@Column(name=COLUMN_NODEID)
	private int id;
	
	/**
	 * 知识点的父节点
	 */
	@Column(name=COLUMN_PARENTID)
	private int parentId;
	
	/**
	 * 知识点的子节点ids，用，隔开
	 */
	@Column(name=COLUMN_CHILDREN_IDS)
	private String childrenIds;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getChildrenIds() {
		return childrenIds;
	}

	public void setChildrenIds(String childrenIds) {
		this.childrenIds = childrenIds;
	}
	
}
