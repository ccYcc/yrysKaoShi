package com.ccc.test.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**考试控制器
 * @author Trible Chen
 *
 */
@Controller
@RequestMapping("/test")
public class ExamController {

	/**开始考试接口
	 * @param selectedLevel
	 * @param selectedIds
	 * @param model
	 * @return
	 */
	@RequestMapping("/startExam")
	public Serializable startTest(String level,String selectedIds,ModelMap model){
		System.out.println(level+" " + selectedIds);
		return "redirect:/jsp/startExam";
	}
}
