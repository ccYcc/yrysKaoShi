package com.ccc.test.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.GlobalValues;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**考试控制器
 * @author Trible Chen
 *
 */
@Controller
@RequestMapping("/test")
public class ExamController {

	@RequestMapping("type/{examType}")
	public String toChooseKnowledge(@PathVariable String examType,ModelMap model){
			model.addAttribute("examType", examType);
			return "chooseKnowledge";
	}
	
	/**开始考试接口
	 * @param examType 考试类型
	 * @param level 考试难度
	 * @param selectedIds 选择考试的知识点
	 * @param model 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/startExam",method = RequestMethod.POST)
	public Serializable startExam(String examType,String level,String selectedIds,
			ModelMap  model,
			HttpSession session){
		UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		Bog.print("user="+user);
		if ( user == null ){
			return "redirect:/jsp/login?result="+GlobalValues.MSG_PLEASE_LOGIN;
		} else {
			model.addAttribute("examType", examType);
			model.addAttribute("level", level);
			model.addAttribute("selectedIds", selectedIds);
			return "startExam";
		}
	}
	
	/**获取练习本的题目列表
	 * @param level 题目难度
	 * @param selectedIds 考试的知识点
	 * @param session 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/json/fetchExerciseQuestions",method = RequestMethod.POST)
	@ResponseBody
	public Serializable fetchExerciseQuestions(String level,String selectedIds,
			HttpSession session,
			ModelMap model){
			UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
			Bog.print("user="+user);
			if ( user == null ){
				return "redirect:/jsp/login?result="+GlobalValues.MSG_PLEASE_LOGIN;
			} else {
				Bog.print("fetchExerciseQuestions="+level+" " + selectedIds );
				List<QuestionInfo> questionInfos =new ArrayList<QuestionInfo>();
				int qnum = 4;
				for ( int i = 0 ; i < qnum ; i++ ){
					QuestionInfo quest = new QuestionInfo();
					quest.setId(i);
					char ans = (char) ('A'+i);
					quest.setAnswer(Character.valueOf(ans)+"");
					quest.setLevel("难");
					questionInfos.add(quest);
				}
				ObjectMapper mapper = new ObjectMapper();
				try {
					return mapper.writeValueAsString(questionInfos);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					return "startExam";
				}
			}
	}
	
	/**下一道题       用于自主测试
	 * @param answerLogs 回答的记录。格式为：qid:ansResult:usedTime,qid:ansResult:usedTime...
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/json/nextQuestion",method = RequestMethod.POST)
	@ResponseBody
	public Serializable nextQuestion(String answerLogs,
			HttpSession session,
			ModelMap model){
		
		UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		System.out.println("user="+user+" answerLogs="+answerLogs);
		if ( user == null ){
			return "redirect:/jsp/login?result="+GlobalValues.MSG_PLEASE_LOGIN;
		} else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				List<UserAnswerLogInfo> result = mapper.readValue(answerLogs, new TypeReference<List<UserAnswerLogInfo>>(){});
				System.out.println("ansLogs"+result);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			QuestionInfo quest = new QuestionInfo();
			int i = (int)(Math.random()*100);
			quest.setId(i);
			quest.setAnswer("C");
			quest.setLevel("一般");
			return null;
		}
	}
	
	/**结束考试
	 * @return
	 */
	public Serializable endExam(){
		return "";
	}
}
