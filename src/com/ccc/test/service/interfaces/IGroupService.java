package com.ccc.test.service.interfaces;

import java.io.Serializable;

import com.ccc.test.pojo.GroupInfo;

/**
 * @author cxl
 * 有关群组的业务
 *
 */
public interface IGroupService {
	/**
	 * @author cxl
	 * 创建班级
	 * @param teacherID
	 * @param groupName
	 * @param description
	 * @return 班级id
	 * @throws Exception 
	 */
	Serializable create_group(Integer teacherID,String groupName,String description) throws Exception;
	/** 删除班级
	 * author cxl
	 * @param groupId 班级id
	 * @param teacherId 老师id
	 * @return msg
	 */
	Serializable deleteGroup(Integer teacherId,Integer groupId);
	/**更新班级信息
	 * @author cxl
	 * @param teacherId 老师id
	 * @param groupId 班级id
	 * @param groupInfo 班级信息
	 * @return 班级id
	 */
	Serializable updateGroup(Integer teacherId,Integer groupId,GroupInfo groupInfo);
	/**查找班级信息
	 * @author cxl
	 * @param requestId
	 * @param groupId
	 * @return 班级信息列表 GroupInfos
	 */
	Serializable queryGroup(Integer requestId,Integer groupId);
	/**查找老师对应的班级列表 仅需查找GroupInfo表
	 * @author cxl
	 * @param requestId 请求者id
	 * @return 班级信息列表 GroupInfos
	 */
	Serializable teaQueryGroups(Integer requestId);
	/**查找班级下的学生信息
	 * @author cxl
	 * @param groupId 班级id
	 * @return 学生userInfos
	 */
	Serializable queryStuList(Integer groupId);
	/**查找学生对应的班级列表
	 * @author cxl
	 * @param requestId
	 * @return 班级信息列表 GroupInfos
	 */
	Serializable stuQueryGroups(Integer requestId);
	
}
