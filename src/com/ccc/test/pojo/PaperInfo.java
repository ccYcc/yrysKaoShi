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

/**试卷信息类
 * @author Trible Chen
 *
 */
@Entity
@Table(name = PaperInfo.TABLE_NAME)
public class PaperInfo implements Serializable{

	private static final long serialVersionUID = -4822594231538842267L;
	
	public static final String TABLE_NAME = "tb_paper";
	public static final String COLUMN_ID = "pid";
	public static final String COLUMN_PAPERURL = "paper_url";
	public static final String COLUMN_NAEM = "name";
	public static final String COLUMN_CREATE_TIME = "create_time";
	public static final String COLUMN_QUESTIONIDS = "questions";
	public static final String COLUMN_TEACHER_ID = "teacher_id";
	/**
	 * 试卷id
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator="generator")
	@Column(name=COLUMN_ID)
	private int id;
	
	
	/**
	 * 试卷文档地址
	 */
	@Column(name=COLUMN_PAPERURL)
	private String paperUrl;
	
	/**
	 * 试卷名字
	 */
	@Column(name=COLUMN_NAEM)
	private String name;

	/**
	 * 试卷上传时间
	 */
	@Column(name=COLUMN_CREATE_TIME)
	private long createTime;
	

	/**
	 * 试卷的题目列表ids，用，隔开
	 */
	@Column(name=COLUMN_QUESTIONIDS)
	private  String questionIds;

	@Column(name=COLUMN_TEACHER_ID)
	private long teacher_id;
	
	@Transient
	List<QuestionInfo> questions;
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPaperUrl() {
		return paperUrl;
	}

	public void setPaperUrl(String paperUrl) {
		this.paperUrl = paperUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public List<QuestionInfo> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionInfo> questions) {
		this.questions = questions;
	}

	

	public long getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(long teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(String questionIds) {
		this.questionIds = questionIds;
	}
	
	
}
