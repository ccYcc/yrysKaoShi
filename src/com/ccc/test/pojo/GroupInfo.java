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

import com.ccc.test.service.impl.UserServiceImpl;
import com.ccc.test.utils.StringUtil;

/**班级对象类
 * @author Trible Chen
 *
 */
@Entity
@Table(name = GroupInfo.TABLE_NAME)
public class GroupInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "tb_group";
	public static final String COLUMN_ID = "gid";
	public static final String COLUMN_OWNERID = "owner_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_CREATETIME = "create_time";
	public static final String COLUMN_VALIDTION = "validtion";
	public static final String COLUMN_DESC = "description";
	
	
	/**
	 * 班级id
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator="generator")
	@Column(name=COLUMN_ID)
	private int id;
	
	/**
	 * 班级创建者id
	 */
	@Column(name=COLUMN_OWNERID)
	private int ownerId;
	
	/**
	 * 班级名字
	 */
	@Column(name=COLUMN_NAME)
	private String name;
	
	/**
	 * 班级描述
	 */
	@Column(name=COLUMN_DESC)
	private String description;
	
	/**
	 * 加入班级验证方式 0表示不用验证，1表示群主同意加入
	 */
	@Column(name=COLUMN_VALIDTION)
	private int validtion;
	
	/**
	 * 班级创建时间
	 */
	@Column(name=COLUMN_CREATETIME)
	private long createTime;

	/**
	 * 班级内的同学
	 */
	@Transient
	List<UserInfo> classmates;
	/**
	 * 班级相关联的试卷
	 */
	@Transient
	private
	List<TeacherPaperInfo> papers;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public List<UserInfo> getClassmates() {
		return classmates;
	}

	public void setClassmates(List<UserInfo> classmates) {
		this.classmates = classmates;
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

	public int getValidtion() {
		return validtion;
	}

	public void setValidtion(int validtion) {
		this.validtion = validtion;
	}
	/**
	 * @param other 设置另外一个班级的部分信息进来
	 */
	public void setGroupInfo(GroupInfo groupInfo){
		name = groupInfo.name;
		description = groupInfo.description;
	}

	public List<TeacherPaperInfo> getPapers() {
		return papers;
	}

	public void setPapers(List<TeacherPaperInfo> papers) {
		this.papers = papers;
	}

	
}
