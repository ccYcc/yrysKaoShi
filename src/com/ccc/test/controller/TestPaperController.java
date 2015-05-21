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
import com.ccc.test.hibernate.dao.interfaces.IknowledgeDao;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.TagInfor;
import com.ccc.test.service.interfaces.IKnowledgeService;
import com.ccc.test.utils.Bog;
import com.ccc.test.service.interfaces.IQuestionService;
import com.ccc.test.service.interfaces.ITeacherService;


/**
 * @author cxl
 *
 */
@Controller
@RequestMapping("/test")
public class TestPaperController {
	
	
	@Autowired

	ITeacherService teacherService;

	@Autowired
	IKnowledgeService knowledgeService;

	@Autowired
	IQuestionService questionService;

	/**上传paper方法
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
//				Serializable ret = paperService.uploadPaper(request, teacher_id, url, question_ids);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return "adminMain";
	}
	
	/**
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
		long create_time = System.currentTimeMillis();
		
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
		

		teacherService.uploadPaper(url, paperName, create_time, questString, teacherId);
		
		return "adminMain";
		
	}

	
}
