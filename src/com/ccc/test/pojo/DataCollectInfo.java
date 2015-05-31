package com.ccc.test.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**答题汇总类
 * @author ycc
 *
 */
@Entity
@Table(name = DataCollectInfo.TABLE_NAME)
public class DataCollectInfo implements Serializable{
	private static final long serialVersionUID = -3360350222547958756L;
	public static final String TABLE_NAME = "tb_data_collect";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_uid = "uid";
	public static final String COLUMN_kid = "kid";
	public static final String COLUMN_kid_name = "kid_name";
	public static final String COLUMN_use_time = "use_time";
	public static final String COLUMN_ans_number = "ans_number";
	public static final String COLUMN_ans_right_number = "ans_right_number";
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator="generator")
	@Column(name=COLUMN_ID)
	private int id;
	@Column(name=COLUMN_uid)
	private int uid;
	@Column(name=COLUMN_kid)
	private int kid;
	
	/**
	 * 知识点名字
	 */
	@Column(name=COLUMN_kid_name)
	private String k_name;
	
	public String getK_name() {
		return k_name;
	}
	public void setK_name(String k_name) {
		this.k_name = k_name;
	}
	@Column(name=COLUMN_use_time)
	private long use_time;
	@Column(name=COLUMN_ans_number)
	private int ans_number;
	@Column(name=COLUMN_ans_right_number)
	private int ans_right_number;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getKid() {
		return kid;
	}
	public void setKid(int kid) {
		this.kid = kid;
	}
	public long getUse_time() {
		return use_time;
	}
	public void setUse_time(long use_time) {
		this.use_time = use_time;
	}
	public int getAns_number() {
		return ans_number;
	}
	public void setAns_number(int ans_number) {
		this.ans_number = ans_number;
	}
	public int getAns_right_number() {
		return ans_right_number;
	}
	public void setAns_right_number(int ans_right_number) {
		this.ans_right_number = ans_right_number;
	}
	
	public void Add(DataCollectInfo b)
	{
		this.setAns_number(b.getAns_number()+this.getAns_number());
		this.setUse_time(this.getUse_time()+b.getUse_time());
		this.setAns_right_number(this.getAns_right_number()+b.getAns_right_number());
	}
}
