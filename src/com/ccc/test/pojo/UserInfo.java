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

/**用户信息类
 * @author Trible Chen
 *
 */
@Entity
@Table(name = UserInfo.TABLE_NAME)
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "tb_userinfo";
	public static final String COLUMN_ID = "uid";
	public static final String COLUMN_USER_NAME = "username";
	public static final String COLUMN_REALNAME = "realname";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_TYPE = "usertype";
	public static final String COLUMN_CREATETIME = "create_time";
	public static final String COLUMN_BIRTHDAY = "birthday";
	public static final String COLUMN_SEX = "sex";
	public static final String COLUMN_HEADURL = "head_url";
	public static final String COLUMN_DESC = "description";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_LAST_LOGIN_TIME = "last_login_time";
	
	/**
	 * 用户id
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator="generator")
	@Column(name=COLUMN_ID)
	private int id;
	
	/**
	 * 用户名
	 */
	@Column(name=COLUMN_USER_NAME)
	private String username;
	
	/**
	 * 用户真实姓名名
	 */
	@Column(name=COLUMN_REALNAME)
	private String realname;
	
	/**
	 * 用户密码
	 */
	@Column(name=COLUMN_PASSWORD)
	private String password;
	
	/**
	 * 用户角色
	 */
	@Column(name=COLUMN_TYPE)
	private String type;
	
	/**
	 * 生日
	 */
	@Column(name=COLUMN_BIRTHDAY)
	private long birthday;
	
	/**
	 * 用户性别
	 */
	@Column(name=COLUMN_SEX)
	private String sex;
	
	/**
	 * 用户简介
	 */
	@Column(name=COLUMN_DESC)
	private String description;
	
	/**
	 * 用户头像url
	 */
	@Column(name=COLUMN_HEADURL)
	private String headUrl;
	
	/**
	 * 用户邮箱
	 */
	@Column(name=COLUMN_EMAIL)
	private String email;
	
	/**
	 * 注册时间 时间戳
	 */
	@Column(name=COLUMN_CREATETIME)
	private long createTime;
	
	/**
	 * 最近登录时间 时间戳 单位ms
	 */
	@Column(name=COLUMN_LAST_LOGIN_TIME)
	private long lastLoginTime;
	
	/**
	 * 班级列表 Transient 表示让hibernate不进行映射
	 */
	@Transient
	List<GroupInfo> classes;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public List<GroupInfo> getClasses() {
		return classes;
	}

	public void setClasses(List<GroupInfo> classes) {
		this.classes = classes;
	}

	public long getBirthday() {
		return birthday;
	}

	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * @param other 设置另外一个用户的部分信息进来
	 */
	public void setUserInfo(UserInfo other){
		email = other.email;
		birthday = other.birthday;
		sex = other.sex;
		if ( StringUtil.isEmpty(other.headUrl) ){
			headUrl = UserServiceImpl.defaultHeadUrl;
		}else{
			headUrl = other.headUrl;
		}
		lastLoginTime = other.lastLoginTime;
		description = other.description;
		realname = other.realname;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
}
