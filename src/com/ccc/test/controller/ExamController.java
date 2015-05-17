package com.ccc.test.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ccc.test.pojo.UserInfo;
import com.ccc.test.utils.GlobalValues;

/**考试控制器
 * @author Trible Chen
 *
 */
@Controller
@RequestMapping("/test")
@SessionAttributes(GlobalValues.SESSION_USER)
public class ExamController {

	/**开始考试接口
	 * @param selectedLevel
	 * @param selectedIds
	 * @param model
	 * @return
	 */
	@RequestMapping("/startExam")
	public Serializable startExam(String level,String selectedIds,
			@ModelAttribute(GlobalValues.SESSION_USER) UserInfo user,
			ModelMap model){
		if ( user == null ){
			return "redirect:/jsp/login";
		} else {
			
		}
		System.out.println(level+" " + selectedIds + user);
		return "redirect:/jsp/startExam";
	}
	
	/**下一道题
	 * @return
	 */
	public Serializable nextQuestion(int qid, String userAnswer,
			@ModelAttribute(GlobalValues.SESSION_USER) UserInfo user,
			ModelMap model){
		return "";
	}
	
	/**结束考试
	 * @return
	 */
	public Serializable endExam(){
		return "";
	}
}
