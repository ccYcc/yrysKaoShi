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
	public static final String COLUMN_REQUEST_ID = "request_id";
	public static final String COLUMN_GROUPID = "group_id";
	public static final String COLUMN_ACCEPT_ID = "accept_id";
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
	@Column(name=COLUMN_REQUEST_ID)
	private int request_id;
	
	/**
	 * 
	 */
	@Column(name=COLUMN_ACCEPT_ID)
	private int accept_id;
	
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

	public int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	public int getAccept_id() {
		return accept_id;
	}

	public void setAccept_id(int accept_id) {
		this.accept_id = accept_id;
	}
	
	
}
