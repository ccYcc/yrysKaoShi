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

/**题目类
 * @author Trible Chen
 *
 */
@Entity
@Table(name = QuestionInfo.TABLE_NAME)
public class QuestionInfo implements Serializable{

	private static final long serialVersionUID = -4822594231538842267L;
	
	public static final String TABLE_NAME = "tb_question";
	public static final String COLUMN_ID = "qid";
	public static final String COLUMN_QUESTIONURL = "question_url";
	public static final String COLUMN_ANSWER = "answer";
	public static final String COLUMN_LEVEL = "level";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_OPTION = "options";
	public static final String COLUMN_FLAG = "flag";
	/**
	 * 题目id
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator="generator")
	@Column(name=COLUMN_ID)
	private int id;
	
	/**
	 * 题目的答案选择范围
	 */
	@Column(name=COLUMN_OPTION)
	private String options;
	
	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

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
	 * 题目类型 如选择题。
	 */
	@Column(name=COLUMN_TYPE)
	private String type;
	/**
	 * author cxl
	 * flag=0:此题目为管理员上传题目
	 * flag=1:此题目为老师上传的试卷中的题目
	 */
	@Column(name=COLUMN_FLAG)
	private int flag;
	/**
	 * 题目的属性，知识点对象列表，根据knowledgeIds填充具体对象
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
