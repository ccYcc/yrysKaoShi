package com.ccc.test.service.interfaces;

import java.io.Serializable;
import java.util.List;

import com.ccc.test.pojo.ValidtionInfo;

/**
 * @author cxl
 * 有关老师的业务
 *
 */
public interface ITeacherService {
	
	/**
	 * author cxl
	 * 老师上传试卷信息
	 * @param paper_url 试卷在服务器上存放的相对url
	 * @param paperName 试卷名称
	 * @param create_time 试卷上传时的时间
	 * @param question_ids 试卷中的题目id
	 * @param teacher_id 老师id
	 * @return
	 * @throws Exception
	 */
	 
	Serializable uploadPaper(String paper_url,
							 String paperName,
							 Long create_time,
							 String question_ids,
							 Integer teacher_id) throws Exception;
	/**
	 * @author cxl
	 * 老师创建班级
	 * @param teacherID
	 * @param groupName
	 * @param description
	 * @return
	 * @throws Exception 
	 */
	Serializable create_group(Integer teacherID,String groupName,String description) throws Exception;
	/**
	 * @author cxl
	 * 老师每次登陆的时候查看是否有新加入请求，如果没有则返回0，如果有则返回请求数
	 * @throws Exception 
	 */
	Serializable hasJoinRequest(Integer teacherID) throws Exception;
	/**
	 * @author cxl
	 * 根据validation信息获取请求者的信息
	 * @param ts
	 * @return
	 * @throws Exception 
	 */
	Serializable fetchInfor(List<ValidtionInfo> validations) throws Exception;
	/**
	 * 
	 * @param group_id 班级id
	 * @param userId  学生id
	 * @param message 老师给学生的留言
	 * @param create_time 创建时间
	 * @return
	 * @throws Exception
	 */
	Serializable handleRequest(Integer groupId,Integer userId,Integer teacherId,String message,long create_time) throws Exception;
	
	
}
