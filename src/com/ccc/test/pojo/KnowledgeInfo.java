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
	public static final String COLUMN_DESC = "description";
	public static final String COLUMN_CREATETIME = "create_time";
	public static final String COLUMN_PID = "pid";
	public static final String COLUMN_RIGHT_SELECT_WEIGHT = "select_weight";
	
	/**
	 * 知识点id
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator="generator")
	@Column(name=COLUMN_ID)
	private int id;
	
	/**
	 * 父节点id
	 */
	@Column(name=COLUMN_PID)
	private Integer pid;
	
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	/**
	 * 知识点名�?
	 */
	@Column(name=COLUMN_NAME)
	private String name;
	
	/**
	 * 知识点描�?
	 */
	@Column(name=COLUMN_DESC)
	private String description;
	
	/**
	 * 知识点创建时�?
	 */
	@Column(name=COLUMN_CREATETIME)
	private long createTime;

	/**
	 * 选择题目的权重，[0,1]，值越大越优先选择
	 */
	@Column(name=COLUMN_RIGHT_SELECT_WEIGHT)
	private Float selectWeight;
	/**
	 * 知识点父节点
	 */
	@Transient
	KnowledgeInfo parentNode;
	
	/**
	 * 知识点的子节�?
	 */
	@Transient
	List<KnowledgeInfo> childrenNodes;
	
	@Override
	public String toString() {
		return "id="+this.id+" pid="+this.pid+" name="+this.name
				+" description="+this.description+" hasChildren="+this.hasChildren;
	};
	
	/**
	 *用于判断是否有孩子节点，在延迟加载时有用 
	 */
	@Transient
	boolean hasChildren;
	
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

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
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

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public Float getSelectWeight() {
		return selectWeight == null ? 1 : selectWeight;
	}

	public void setSelectWeight(Float selectWeight) {
		this.selectWeight = selectWeight;
	}
	
	
}
