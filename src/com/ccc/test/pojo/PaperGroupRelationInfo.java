package com.ccc.test.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**试卷与班级关系类
 * @author cxl
 *
 */
@Entity
@Table(name = PaperGroupRelationInfo.TABLE_NAME)
public class PaperGroupRelationInfo {
	
	public static final String TABLE_NAME = "tb_paper_group";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PAPERID = "pid";
	public static final String COLUMN_GROUPID = "gid";
	public static final String COLUMN_CREATETIME = "createTime";
	
	/**
	 * 行id
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "identity")
	@GeneratedValue(generator="generator")
	@Column(name=COLUMN_ID)
	private int id;
	/**
	 * 试卷id
	 */
	@Column(name=COLUMN_PAPERID)
	private int paperId;
	
	/**
	 * 班级id
	 */
	@Column(name=COLUMN_GROUPID)
	private int groupId;

	/**
	 * 建立关系时间
	 */
	@Column(name=COLUMN_CREATETIME)
	private long createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPaperId() {
		return paperId;
	}

	public void setPaperId(int paperId) {
		this.paperId = paperId;
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
