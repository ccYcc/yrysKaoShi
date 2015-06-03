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

	/**我的试卷信息类
	 * @author cxl
	 *
	 */
	@Entity
	@Table(name = DiyPaperInfo.TABLE_NAME)

public class DiyPaperInfo implements Serializable{
	private static final long serialVersionUID = -8712279729484486397L;
	public static final String TABLE_NAME = "tb_diy_paper";
	public static final String COLUMN_ID = "pid";
	public static final String COLUMN_PAPER_NAME = "paperName";
	public static final String COLUMN_CREATE_TIME = "createTime";
	public static final String COLUMN_ANSWER_LOGS = "answer_logs";
	public static final String COLUMN_WRONG_COUNTS = "wrongCounts";
	public static final String COLUMN_RIGHT_COUNTS = "rightCounts";
	public static final String COLUMN_USE_TIME = "useTime";
	public static final String COLUMN_CHOOSE_KNOWLEDGES = "chooseKnowledges";
	public static final String COLUMN_PAPER_LEVEL = "paperLevel";
	public static final String COLUMN_LEARN_LEVEL = "learnLevel";
	public static final String COLUMN_GOOD_KNOWLEDGES = "goodKnowledges";
	public static final String COLUMN_BAD_KNOWLEDGES = "badKnowledges";
	public static final String COLUMN_MID_KNOWLEDGES = "midKnowledges";
	public static final String COLUMN_RECOMMEND_QUESTIONS = "recommendQuestions";
	
	
	/**
	 * 试卷id
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator="generator")
	@Column(name=COLUMN_ID)
	private int pid;
	
	/**
	 * 试卷名称
	 */
	@Column(name=COLUMN_PAPER_NAME)
	private String paperName;
	
	/**
	 * 试卷关联的题目列表
	 */
	@Column(name=COLUMN_ANSWER_LOGS)
	private String answer_logs;
	
	/**
	 * 回答试卷所用时间
	 */
	@Column(name=COLUMN_USE_TIME)
	private String useTime;
	
	/**
	 * 试卷关联的知识点
	 */
	@Column(name=COLUMN_CHOOSE_KNOWLEDGES)
	private String chooseKnowledges;
	
	/**
	 * 试卷掌握情况
	 */
	@Column(name=COLUMN_LEARN_LEVEL)
	private String learnLevel;
	
	/**
	 * 试卷难度
	 */
	@Column(name=COLUMN_PAPER_LEVEL)
	private String paperLevel;
	
	/**
	 * 没有掌握知识点
	 */
	@Column(name=COLUMN_BAD_KNOWLEDGES)
	private String badKnowledges;
	
	/**
	 * 已经掌握知识点
	 */
	@Column(name=COLUMN_GOOD_KNOWLEDGES)
	private String goodKnowledges;
	
	/**
	 * 还需提高的知识点
	 */
	@Column(name=COLUMN_MID_KNOWLEDGES)
	private String midKnowledges;
	
	/**
	 * 回答正确题目列表
	 */
	@Column(name=COLUMN_RIGHT_COUNTS)
	private String rightCounts;
	
	/**
	 * 回答错误题目列表
	 */
	@Column(name=COLUMN_WRONG_COUNTS)
	private String wrongCounts;
	
	/**
	 * 推荐题目列表
	 */
	@Column(name=COLUMN_RECOMMEND_QUESTIONS)
	private String recommendQuestions;
	
	
	@Transient
	private
	List<UserAnswerLogInfo> answerLogInfos;
	
	@Transient
	private
	List<QuestionInfo> questionInfos;
	
	@Transient
	private
	List<QuestionInfo> recommendQuestInfos;
	
	
	@Transient
	private
	List<KnowledgeInfo> chooseKnowledgeInfos;
	
	@Transient
	private
	List<KnowledgeInfo> goodKnowledgeInfos;
	
	@Transient
	private
	List<KnowledgeInfo> badKnowledgeInfos;
	
	@Transient
	private
	List<KnowledgeInfo> midKnowledgeInfos;
	
	
	public String getPaperName() {
		return paperName;
	}


	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}



	public String getUseTime() {
		return useTime;
	}


	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}


	public String getChooseKnowledges() {
		return chooseKnowledges;
	}


	public void setChooseKnowledges(String chooseKnowledges) {
		this.chooseKnowledges = chooseKnowledges;
	}


	public String getLearnLevel() {
		return learnLevel;
	}


	public void setLearnLevel(String learnLevel) {
		this.learnLevel = learnLevel;
	}


	public String getPaperLevel() {
		return paperLevel;
	}


	public void setPaperLevel(String paperLevel) {
		this.paperLevel = paperLevel;
	}


	public String getBadKnowledges() {
		return badKnowledges;
	}


	public void setBadKnowledges(String badKnowledges) {
		this.badKnowledges = badKnowledges;
	}


	public String getGoodKnowledges() {
		return goodKnowledges;
	}


	public void setGoodKnowledges(String goodKnowledges) {
		this.goodKnowledges = goodKnowledges;
	}


	public String getMidKnowledges() {
		return midKnowledges;
	}


	public void setMidKnowledges(String midKnowledges) {
		this.midKnowledges = midKnowledges;
	}


	public String getRightCounts() {
		return rightCounts;
	}


	public void setRightCounts(String rightCounts) {
		this.rightCounts = rightCounts;
	}


	public String getWrongCounts() {
		return wrongCounts;
	}


	public void setWrongCounts(String wrongCounts) {
		this.wrongCounts = wrongCounts;
	}


	public String getRecommendQuestions() {
		return recommendQuestions;
	}


	public void setRecommendQuestions(String recommendQuestions) {
		this.recommendQuestions = recommendQuestions;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	/**
	 * 试卷生成时间
	 */
	@Column(name=COLUMN_CREATE_TIME)
	private String createTime;

	public int getPid() {
		return pid;
	}


	public void setPid(int pid) {
		this.pid = pid;
	}


	public String getAnswer_logs() {
		return answer_logs;
	}


	public void setAnswer_logs(String answer_logs) {
		this.answer_logs = answer_logs;
	}


	public List<UserAnswerLogInfo> getAnswerLogInfos() {
		return answerLogInfos;
	}


	public void setAnswerLogInfos(List<UserAnswerLogInfo> answerLogInfos) {
		this.answerLogInfos = answerLogInfos;
	}


	public List<QuestionInfo> getQuestionInfos() {
		return questionInfos;
	}


	public void setQuestionInfos(List<QuestionInfo> questionInfos) {
		this.questionInfos = questionInfos;
	}


	public List<QuestionInfo> getRecommendQuestInfos() {
		return recommendQuestInfos;
	}


	public void setRecommendQuestInfos(List<QuestionInfo> recommendQuestInfos) {
		this.recommendQuestInfos = recommendQuestInfos;
	}


	public List<KnowledgeInfo> getChooseKnowledgeInfos() {
		return chooseKnowledgeInfos;
	}


	public void setChooseKnowledgeInfos(List<KnowledgeInfo> chooseKnowledgeInfos) {
		this.chooseKnowledgeInfos = chooseKnowledgeInfos;
	}


	public List<KnowledgeInfo> getGoodKnowledgeInfos() {
		return goodKnowledgeInfos;
	}


	public void setGoodKnowledgeInfos(List<KnowledgeInfo> goodKnowledgeInfos) {
		this.goodKnowledgeInfos = goodKnowledgeInfos;
	}


	public List<KnowledgeInfo> getBadKnowledgeInfos() {
		return badKnowledgeInfos;
	}


	public void setBadKnowledgeInfos(List<KnowledgeInfo> badKnowledgeInfos) {
		this.badKnowledgeInfos = badKnowledgeInfos;
	}


	public List<KnowledgeInfo> getMidKnowledgeInfos() {
		return midKnowledgeInfos;
	}


	public void setMidKnowledgeInfos(List<KnowledgeInfo> midKnowledgeInfos) {
		this.midKnowledgeInfos = midKnowledgeInfos;
	}

	
}
