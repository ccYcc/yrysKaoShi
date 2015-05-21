package com.ccc.test.controller;

import java.io.IOException;
import java.io.Serializable;
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
import com.ccc.test.service.interfaces.IPaperService;
import com.ccc.test.utils.Bog;

/**
 * @author cxl
 *
 */
@Controller
@RequestMapping("/test")
public class TestPaperController {
	
	
	@Autowired
	IPaperService paperService;

	@Autowired
	IKnowledgeService knowledgeService;
	
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
				Serializable ret = paperService.uploadPaper(request, teacher_id, url, question_ids);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return "adminMain";
	}
	
	@RequestMapping("/tag_question")
	public String tag_question(HttpServletRequest request,
			HttpServletResponse response,List<QuestionInfo> questInfors)
	{
		
		
		
		
		return null;
		
	}

	
	@RequestMapping("/test_know")
	public String testKnowledges(HttpServletRequest request,
			HttpServletResponse response)
	{
		List<KnowledgeInfo> dao = null;
		try {
			dao = (List<KnowledgeInfo>) knowledgeService.getKnowlegeByID(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(KnowledgeInfo dd : dao)
		{
			Bog.print(""+dd.toString());
		}
		
		Bog.print("++++++++++++++++++");
		try {
			dao = (List<KnowledgeInfo>) knowledgeService.getKnowlegeByID(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(KnowledgeInfo dd : dao)
		{
			Bog.print(""+dd.toString());
		}
		return null;
		
	}
}
