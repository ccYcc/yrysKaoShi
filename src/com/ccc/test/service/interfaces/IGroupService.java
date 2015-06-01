package com.ccc.test.service.interfaces;

import java.io.Serializable;
import java.util.List;

import com.ccc.test.pojo.GroupInfo;
import com.ccc.test.pojo.ValidtionInfo;

/**
 * @author cxl
 * 有关班级的业务
 *
 */
public interface IGroupService {
	/**
	 * @author cxl
	 * 创建班级(对所有用户开发的接口)
	 * @param teacherID
	 * @param groupName
	 * @param description
	 * @return 班级id 错误：返回MsgInfo
	 * @throws Exception 
	 */
	Serializable create_group(Integer requestId,String groupName,
			String description) throws Exception;
	
	/** 删除班级
	 * author cxl
	 * @param groupId 班级id
	 * @param teacherId 老师id
	 * @return msg
	 */
	Serializable deleteGroup(Integer groupId);
	
	/**更新班级信息
	 * @author cxl
	 * @param groupInfo 班级信息
	 * @return MsgInfo
	 */
	Serializable updateGroup(GroupInfo groupInfo);
	
	/**查找特定班级详细信息(学生、老师同一接口)
	 * @author cxl
	 * @param requestId
	 * @param groupId
	 * @return 班级信息列表 GroupInfo 错误：返回MsgInfo
	 */
	Serializable queryGroup(Integer groupId);
	
	/**查找用户应的班级列表     
	 * 如果是老师(0)： 仅需查找GroupInfo表 
	 * 如果是学生(1)：则先查找查找关系表，在查找info表
	 * @author cxl
	 * @param requestId 请求者id
	 * @param userType 用户角色
	 * @return 正确：班级信息列表 GroupInfos 错误：返回MsgInfo
	 * @throws Exception 
	 */
	Serializable QueryGroups(Integer requestId,Integer userType) throws Exception;
	
	/**查找班级下的学生信息
	 * @author cxl
	 * @param groupId 班级id
	 * @return 学生userInfos 错误：返回MsgInfo
	 * @throws Exception 
	 */
	Serializable queryStuList(Integer groupId) throws Exception;
	
	/**查看是否有新消息
	 * @author cxl
	 * @param acceptId 接收者id
	 * @return validtionInfos 错误返回： MsgInfo
	 */
	Serializable hasNewInfo(Integer acceptId);
	
	/**
	 * @author cxl
	 * 根据validation信息获取请求者（学生）的信息
	 * @param ts
	 * @return userInfos
	 * @throws Exception 
	 */
	Serializable fetchUser(List<ValidtionInfo> validations) throws Exception;
	
	/**根据validation信息获取班级信息
	 * @author cxl
	 * @param validations
	 * @return
	 */
	Serializable fetchGroup(List<ValidtionInfo> validations);
	
	/**查找班级中的所有paper信息
	 * 
	 * @param groupId
	 * @return groupInfo false: MsgInfo
	 */
	Serializable fetchPaper(Integer groupId);
	
	/**
	 * 根据试卷id 获取试卷信息
	 * @param paperId
	 * @return PaperInfo false:MsgInfo
	 */
	Serializable fetchPaperById(Integer paperId);
	
	/**查找试卷中的题目
	 * @author cxl
	 * @param paperId
	 * @return QuestionInfos false:MsgInfo
	 */
	Serializable fetchQuestions(Integer paperId);
}
