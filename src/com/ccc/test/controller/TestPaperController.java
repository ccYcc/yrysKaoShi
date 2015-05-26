package com.ccc.test.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccc.test.pojo.GroupInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.pojo.ValidtionInfo;
import com.ccc.test.service.interfaces.IGroupService;
import com.ccc.test.service.interfaces.ITeacherService;
import com.ccc.test.service.interfaces.IUserService;
import com.ccc.test.service.interfaces.IKnowledgeService;
import com.ccc.test.service.interfaces.IQuestionService;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.QuestionInfo;

/**
 * @author cxl
 *
 */
@Controller
@RequestMapping("/test")
public class TestPaperController {
	
	
	@Autowired

	IUserService userService;
	
	@Autowired

	ITeacherService teacherService;

	@Autowired
	IKnowledgeService knowledgeService;

	@Autowired
	IQuestionService questionService;
	
	@Autowired IGroupService groupService;

	/**测试上传paper方法
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/uploadPaper")
	public String uploadPaper(
			HttpServletRequest request,
			HttpServletResponse response,
			Integer teacher_id ,
			String url,
			String question_ids)
	{
		
		   teacher_id = 1;
		   url="/test";
		   question_ids="1,2,3";
		   System.out.println("TEST UPLOAD_PAPER");
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
		return "adminMain";
	}
	
	/**
	 * 测试老师标记试卷题目
	 * @param request
	 * @param response
	 * @param tagInfors 封装对每道题的tag
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/tag_question")
	public String tag_question(HttpServletRequest request,
							   HttpServletResponse response,
							   String url,
							   String paperName,
							   Integer teacherId) throws Exception
	{
		/*
		 * 模拟前端传参，模拟生成10道题，以及对应的tag
		 */
		teacherId = 1;
		url="/test";
		paperName = "/test";
		ArrayList<QuestionInfo> testQuestions = new ArrayList<QuestionInfo>();
		for(int i=0;i<10;i++)
		{
			QuestionInfo questionInfo = new QuestionInfo();
			List<KnowledgeInfo> knowledges = new ArrayList<KnowledgeInfo>();
			for(int j=0;j<10;j++)
			{
				KnowledgeInfo knowledgeInfo = new KnowledgeInfo();
				knowledgeInfo.setId(j);
				knowledges.add(knowledgeInfo);
			}
			questionInfo.setKnowledges(knowledges);
//			标记题目属性
			questionInfo.setFlag(0);
			testQuestions.add(questionInfo);
			questionInfo.setQuestionUrl("/test_url");
		
		}
		System.out.println("CXL_TEST UPLOAD_PAPER!");
//		将paper中的题目写入到题目数据表中
		questionService.uploadPaperQuest(testQuestions);
		for(QuestionInfo questionInfo:testQuestions)
		{
			System.out.println("CXL_TEST : "+questionInfo.getId());
		}
//		将题目和知识点的关系写入到题目_知识点关系表中
		questionService.uploadQuestKnowledge(testQuestions);
//		将paper信息写入到试卷信息表中
		StringBuffer sb = new StringBuffer();
		for(QuestionInfo questionInfo:testQuestions)
		{
			int qid = questionInfo.getId();
			sb.append(String.valueOf(qid)+",");
		}
		String questString = sb.toString();
		teacherService.uploadPaper(url, paperName, questString, teacherId);
		return "adminMain";
		
	}
	/**
	 * 测试老师每次登陆界面显示是否有请求信息
	 * @param request
	 * @param response
	 * @param teacherId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/requst_query")
	public String requst_query(HttpServletRequest request,
							   HttpServletResponse response,
							   Integer teacherId) throws Exception
	   {
			teacherId = 2;
			@SuppressWarnings("unchecked")
			//获取请求信息
			List<ValidtionInfo> validations = (List<ValidtionInfo>) groupService.hasNewInfo(teacherId);
			//获取请求者信息
			List<UserInfo> userInfos = (List<UserInfo>) groupService.fetchUser(validations); 
			//获取班级信息
			List<GroupInfo> groupInfos = (List<GroupInfo>) groupService.fetchGroup(validations);
			System.out.println("CXL_TEST: "+"您有："+validations.size()+" 个请求");
			return null;
	   }
/**
 * 测试老师处理请求信息
 * @param request
 * @param response
 * @param group_id
 * @param userId
 * @param teacherId
 * @param message
 * @return
 * @throws Exception
 */
	@RequestMapping("/handleRequest")
	public String handleRequest(HttpServletRequest request,
			   HttpServletResponse response,
			   Integer group_id,Integer userId,Integer teacherId,String message) throws Exception
	{
		group_id = 5;
		userId = 3;
		message = "欢迎加入";
		teacherId = 2;
		/**
		 * @param handleType 处理类型 0：拒绝 1：同意
		 */
		int handleType = 1;
		MsgInfo msg =  (MsgInfo) teacherService.
				handleRequest(group_id,userId,teacherId,message,handleType);
		System.out.println("CXL_TEST_"+msg.getMessage());
		return "adminMain";
	}
	
/**
 * 测试老师创建班级
 * @param request
 * @param response
 * @param teacherID
 * @param groupName
 * @param createTime
 * @param description
 * @return
 * @throws Exception
 */
	@RequestMapping("/createGroup")
	public String createGroup(HttpServletRequest request,
			   HttpServletResponse response,
			   Integer teacherID,String groupName,Long createTime,String description) throws Exception
	{
		teacherID = 2;
		groupName = "高一二班";
		description = "二笔班";
		MsgInfo msg = new MsgInfo();
		msg = (MsgInfo) teacherService.create_group(teacherID, groupName, description);
		System.out.println("CXL_TEST_"+msg.getMessage());
		return "adminMain";
		
	}
	
/**
 * 测试老师更新班级信息
 * @param request
 * @param response
 * @param teacherID
 * @param groupId
 * @param groupName
 * @param description
 * @return
 * @throws Exception
 */
	@RequestMapping("/updateGroup")
	public String updateGroup(HttpServletRequest request,
			   HttpServletResponse response,
			   Integer teacherID,
			   Integer groupId,
			   String groupName,
			   String description) throws Exception
	{
		
		teacherID = 2;
		groupName = "高一二班";
		description = "二bi班";
		groupId=6;
		MsgInfo msg = new MsgInfo();
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.setId(groupId);
		groupInfo.setName(groupName);
		groupInfo.setDescription(description);
		groupInfo.setOwnerId(teacherID);
		msg=(MsgInfo) groupService.updateGroup(groupInfo);
		System.out.println("CXL_TEST_"+msg.getMessage());
		return "adminMain";
	}
	
/**
 * 测试学生加入班级
 * @param request
 * @param response
 * @param groupId
 * @param requestId
 * @param acceptId
 * @param createTime
 * @param message
 * @return
 * @throws Exception
 */
	@RequestMapping("/joinGroup")
	public String joinGroup(HttpServletRequest request,
			   HttpServletResponse response,
			   Integer groupId,
			   Integer requestId,
			   Integer acceptId,
			   Long createTime,
			   String message) throws Exception
	{
		groupId = 5;
		requestId = 3;
		message = "老师您好，我是xx";
		acceptId = 2;
		createTime = System.currentTimeMillis();
		//处理请求信息
		MsgInfo msg =  (MsgInfo) userService.
				joinGroup(requestId, acceptId, groupId, message, createTime);
		System.out.println("CXL_TEST_"+msg.getMessage());
		return "adminMain";
	}
/**
 * 测试学生退出班级
 * @param request
 * @param response
 * @param groupId 
 * @param requestId
 * @return
 * @throws Exception
 */
	@RequestMapping("/quitGroup")
	public String quitGroup(HttpServletRequest request,
			   HttpServletResponse response,
			   Integer groupId,
			   Integer requestId) throws Exception
	{
		groupId = 5;
		requestId = 3;
		MsgInfo msg =  (MsgInfo) userService.quitGroup(requestId, groupId);
		System.out.println("CXL_TEST_"+msg.getMessage());
		return "adminMain";
	}
	/**
	 * 查询用户信息
	 * @param request
	 * @param response
	 * @param groupId 
	 * @param requestId
	 * @return
	 * @throws Exception
	 */
		@RequestMapping("/searchUser")
		public String searchUser(HttpServletRequest request,
				   HttpServletResponse response,
				   String userName,
				   String realName) throws Exception
		{
			userName="chenchuibo";
			realName="xx";
			Serializable o = userService.seachUser(userName, realName);
			if (o instanceof MsgInfo) {
				MsgInfo msgInfo = (MsgInfo) o;
				System.out.println("CXL_TEST_"+msgInfo.getMessage());
			}
			else {
				List<UserInfo> userInfos=  (List<UserInfo>) o;
				for(UserInfo user:userInfos)
					System.out.println("CXL_TEST_"+user.getRealname()+"_"+user.getUsername());
			}
			
			return "adminMain";
		}
}
