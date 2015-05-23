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

import com.ccc.test.service.interfaces.IQuestionService;
import com.ccc.test.utils.Bog;

@Controller
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	IQuestionService questService;

	/**上传题目方法
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/uploadQuestion")
	public String uploadQuestion(
			HttpServletRequest request,
			HttpServletResponse response,
			String knowledges,
			String answer,
			String level){
		
			try {
				Serializable ret = questService.uploadQuestion(request, knowledges, answer, level);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return "adminMain";
	}
	
	@RequestMapping("/test")
	public String Test(
			HttpServletRequest request,
			HttpServletResponse response,
			String knowledges,
			String answer,
			String level){
		Bog.print("dsadasdsa");
		try {
			
			questService.getQuestionsByRandom(1, 1+"",2);
			List<Integer>list=new ArrayList<Integer>();
			list.add(1);
			list.add(2);
			questService.getQuestionsByRandom(list, 1+"",2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			Bog.print(e.toString());
		}
		
		return "";
	}
}
