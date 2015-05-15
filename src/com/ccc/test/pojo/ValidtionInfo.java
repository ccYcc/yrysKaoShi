package com.ccc.test.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/*请求验证信息类
 * @author Trible Chen
 *
 */
@Entity
@Table(name = ValidtionInfo.TABLE_NAME)
public class ValidtionInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "tb_validation";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_USERID = "uid";
	public static final String COLUMN_GROUPID = "gid";
	public static final String COLUMN_CREATETIME = "create_time";
	
	/**
	 * 行id
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator="generator")
	@Column(name=COLUMN_ID)
	private int id;
	
	/**
	 * 用户id
	 */
	@Column(name=COLUMN_USERID)
	private int userId;
	
	/**
	 * 班级id
	 */
	@Column(name=COLUMN_GROUPID)
	private int groupId;

	/**
	 * 请求时间
	 */
	@Column(name=COLUMN_CREATETIME)
	private long createTime;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	
}
