package com.ccc.test.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**知识点类
 * @author Trible Chen
 *
 */
@Entity
@Table(name = KnowledgeInfo.TABLE_NAME)
public class KnowledgeInfo implements Serializable{

	private static final long serialVersionUID = -4822594231538842267L;
	
	public static final String TABLE_NAME = "tb_knowledge_node";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DESC = "decription";
	public static final String COLUMN_CREATETIME = "create_time";
	
	/**
	 * 知识点id
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator="generator")
	@Column(name=COLUMN_ID)
	private int id;
	
	/**
	 * 知识点名字
	 */
	@Column(name=COLUMN_NAME)
	private String name;
	
	/**
	 * 知识点名字
	 */
	@Column(name=COLUMN_DESC)
	private String description;
	
	/**
	 * 知识点名字
	 */
	@Column(name=COLUMN_CREATETIME)
	private String createTime;

	/**
	 * 知识点父节点
	 */
	@Transient
	KnowledgeInfo parentNode;
	
	/**
	 * 知识点的子节点
	 */
	@Transient
	List<KnowledgeInfo> childrenNodes;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public KnowledgeInfo getParentNode() {
		return parentNode;
	}

	public void setParentNode(KnowledgeInfo parentNode) {
		this.parentNode = parentNode;
	}

	public List<KnowledgeInfo> getChildrenNodes() {
		return childrenNodes;
	}

	public void setChildrenNodes(List<KnowledgeInfo> childrenNodes) {
		this.childrenNodes = childrenNodes;
	}
	
	
}
