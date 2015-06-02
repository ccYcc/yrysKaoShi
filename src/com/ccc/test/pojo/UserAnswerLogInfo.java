package com.ccc.test.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**用户回答题目记录表
 * @author Trible Chen
 *
 */
@Entity
@Table(name = UserAnswerLogInfo.TABLE_NAME)
public class UserAnswerLogInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "tb_answer_log";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_UID = "uid";
	public static final String COLUMN_QID = "qid";
	public static final String COLUMN_USETIME = "use_time";
	public static final String COLUMN_ANSRET = "ans_result";
	public static final String COLUMN_RIGHT_ANSR = "right_answer";
	public static final String COLUMN_USER_ANSR = "user_answer";
	
	/**
	 * 用户id
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator="generator")
	@Column(name=COLUMN_ID)
	int id;
	
	/**
	 * 题目id
	 */
	@Column(name=COLUMN_QID)
	int qid;
	
	/**
	 * 用户id
	 */
	@Column(name=COLUMN_UID)
	int uid;
	
	/**
	 * 回答题目使用时间 单位秒s
	 */
	@Column(name=COLUMN_USETIME)
	int usedTime;
	
	/**
	 * 回答结果。0代表正确，1代表错误，2未回答
	 */
	@Column(name=COLUMN_ANSRET)
	int ansResult;

	/**
	 * 学生选择的答案
	 */
	@Column(name=COLUMN_USER_ANSR)
	private
	int user_answer;
	
	/**
	 * 正确答案
	 */
	@Column(name=COLUMN_RIGHT_ANSR)
	private
	int right_answer;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQid() {
		return qid;
	}

	public void setQid(int qid) {
		this.qid = qid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(int usedTime) {
		this.usedTime = usedTime;
	}

	public int getAnsResult() {
		return ansResult;
	}

	public void setAnsResult(int ansResult) {
		this.ansResult = ansResult;
	}

	public int getUser_answer() {
		return user_answer;
	}

	public void setUser_answer(int user_answer) {
		this.user_answer = user_answer;
	}

	public int getRight_answer() {
		return right_answer;
	}

	public void setRight_answer(int right_answer) {
		this.right_answer = right_answer;
	}
	
	

}
