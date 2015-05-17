package com.ccc.test.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**知识点与题目关系类
 * @author ycc
 *
 */
@Entity
@Table(name = KnowledgeQuestionRelationInfo.TABLE_NAME)
public class KnowledgeQuestionRelationInfo implements Serializable{

	private static final long serialVersionUID = -4981814790140427983L;
	public static final String TABLE_NAME = "tb_quest_knowledge";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_QuestionID = "qid";
	public static final String COLUMN_Knoeledge_ID = "kid";
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator="generator")
	@Column(name=COLUMN_ID)
	private int id;

	/**
	 * 知识点的父节点
	 */
	@Column(name=COLUMN_QuestionID)
	private int questionId;
	
	/**
	 * 知识点的子节点ids，用，隔开
	 */
	@Column(name=COLUMN_Knoeledge_ID)
	private int KnoeledgeId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getKnoeledgeId() {
		return KnoeledgeId;
	}

	public void setKnoeledgeId(int knoeledgeId) {
		KnoeledgeId = knoeledgeId;
	}


}
