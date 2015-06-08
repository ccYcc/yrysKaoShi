package com.ccc.test.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccc.test.exception.SimpleHandleException;
import com.ccc.test.service.interfaces.IQuestionService;
import com.ccc.test.utils.Bog;

@Controller
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	IQuestionService questService;
	SimpleHandleException simplehandleException = new SimpleHandleException();
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
			String level,
			ModelMap model){
		
			try {
				Serializable ret = questService.uploadQuestion(request, knowledges, answer, level);
				if ( ret == null ){
					model.addAttribute("result", "上传成功");
				} else {
					model.addAttribute("result", ret);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				simplehandleException.handle(e, model);
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
