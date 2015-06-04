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
	public static final String COLUMN_AVG_TIME = "avg_time";
	public static final String COLUMN_WRONG_COUNT = "wrong_count";
	public static final String COLUMN_RIGHT_COUNT = "right_count";
	
	public QuestionInfo()
	{
		this.avgTime=0f;
		this.wrongCount=0;
		this.rightCount=0;
	}
	
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
	 * 完成这个题目的平均用时。
	 */
	@Column(name=COLUMN_AVG_TIME)
	private Float avgTime;
	/**
	 * 答错这道题的人数。
	 */
	@Column(name=COLUMN_WRONG_COUNT)
	private Integer wrongCount;
	/**
	 * 答对这道题的人数。
	 */
	@Column(name=COLUMN_RIGHT_COUNT)
	private Integer rightCount;
	
	/**
	 * author cxl
	 * flag=0:此题目为管理员上传题目
	 * flag=1:此题目为老师上传的试卷中的题目
	 */
	@Column(name=COLUMN_FLAG)
	private int flag;



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


	public float getAvgTime() {
		return avgTime == null ? 0 : avgTime;
	}

	public void setAvgTime(Float avgTime) {
		this.avgTime = avgTime;
	}

	public int getWrongCount() {
		return wrongCount == null ? 0 : wrongCount;
	}

	public void setWrongCount(Integer wrongCount) {
		this.wrongCount = wrongCount;
	}

	public int getRightCount() {
		return rightCount == null ? 0 : rightCount;
	}

	public void setRightCount(Integer rightCount) {
		this.rightCount = rightCount;
	}

}
