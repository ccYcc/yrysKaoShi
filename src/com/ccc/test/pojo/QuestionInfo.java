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

@Entity
@Table(name = QuestionInfo.TABLE_NAME)
public class QuestionInfo implements Serializable{

	private static final long serialVersionUID = -4822594231538842267L;
	
	public static final String TABLE_NAME = "tb_question";
	public static final String COLUMN_ID = "qid";
	public static final String COLUMN_QUESTIONURL = "question_url";
	public static final String COLUMN_ANSWER = "answer";
	public static final String COLUMN_LEVEL = "level";
	
	/**
	 * 题目id
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator="generator")
	@Column(name=COLUMN_ID)
	private int id;
	
	/**
	 * 题目图片地址
	 */
	@Column(name=COLUMN_QUESTIONURL)
	private String questionUrl;
	
	/**
	 * 题目的答案
	 */
	@Column(name=COLUMN_ANSWER)
	private String answer;
	
	/**
	 * 题目难易程度
	 */
	@Column(name=COLUMN_LEVEL)
	private String level;

	/**
	 * 题目的属性，知识点列表
	 */
	@Transient
	List<KnowledgeInfo> knowledges;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestionUrl() {
		return questionUrl;
	}

	public void setQuestionUrl(String questionUrl) {
		this.questionUrl = questionUrl;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<KnowledgeInfo> getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(List<KnowledgeInfo> knowledges) {
		this.knowledges = knowledges;
	}

	
}
