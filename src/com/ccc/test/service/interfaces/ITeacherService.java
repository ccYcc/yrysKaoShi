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
	 * @param question_ids 试卷中的题目id
	 * @param teacher_id 老师id
	 * @param gids 试卷关联的班级
	 * @return paperId false: MsgInfo
	 * @throws Exception
	 */
	Serializable uploadPaper(String paper_url,
							 String paperName,
							 String question_ids,
							 Integer teacher_id,
							 String gids) throws Exception;
	
	/**老师删除试卷
	 * @author cxl
	 * @param paperId 试卷id
	 * @param groupId 班级id
	 * @return MsgInfo
	 */
	Serializable deletePaper(Integer paperId,Integer groupId);
	
	/**
	 * @author cxl
	 * 老师创建班级
	 * @param teacherID
	 * @param groupName
	 * @param description
	 * @return msg
	 * @throws Exception 
	 */
	Serializable create_group(Integer teacherID,
							  String groupName,
							  String description) throws Exception;
	
	
	/**
	 * 
	 * @param group_id 班级id
	 * @param userId  学生id
	 * @param message 老师给学生的留言
	 * @param create_time 创建时间
	 * @param handleType 处理意见 0：拒绝/忽略请求 1：接受请求
	 * @return msg
	 * @throws Exception
	 */
	Serializable handleRequest(Integer groupId,
							   Integer requestId,
							   Integer acceptId,
							   String message,
							   Integer handleType) throws Exception;
	
	/**
	 * 老师删除验证信息
	 * @author cxl
	 * @param requestId 信息发起者id	
	 * @param groupId  班级id
	 * @return msg
	 * @throws Exception 
	 */
	Serializable deleteValidate(Integer requestId,Integer groupId) throws Exception;
	
}
