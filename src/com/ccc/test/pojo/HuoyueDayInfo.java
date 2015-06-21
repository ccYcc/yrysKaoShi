package com.ccc.test.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * @author Trible Chen
 * 统计活跃用户信息类 日活跃
 *
 */
@Entity
@Table(name = HuoyueDayInfo.TABLE_NAME)
public class HuoyueDayInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "tb_huoyue_day_statistic";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_HUOYUE_DATE = "huoyuedate";
	public static final String COLUMN_HUOYUE_NUM = "huoyuenum";
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator="generator")
	@Column(name=COLUMN_ID)
	private int id;
	
	/**
	 * 活跃时间 时间戳格式 long
	 */
	@Column(name=COLUMN_HUOYUE_DATE)
	private long huoYueDate;
	
	/**
	 * 活跃人数
	 */
	@Column(name=COLUMN_HUOYUE_NUM)
	private int huoYueNum;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getHuoYueDate() {
		return huoYueDate;
	}

	public void setHuoYueDate(long huoYueDate) {
		this.huoYueDate = huoYueDate;
	}

	public int getHuoYueNum() {
		return huoYueNum;
	}

	public void setHuoYueNum(int huoYueNum) {
		this.huoYueNum = huoYueNum;
	}

}
