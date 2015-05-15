package com.ccc.test.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccc.test.service.interfaces.IQuestionService;

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
}
