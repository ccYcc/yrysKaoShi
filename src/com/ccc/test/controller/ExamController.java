package com.ccc.test.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;

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
	 * @param selectedLevel
	 * @param selectedIds
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/startExam",method = RequestMethod.POST)
	public Serializable startExam(String examType,String level,String selectedIds,
			ModelMap  model,
			HttpSession session){
		UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		System.out.println("user="+user);
		if ( user == null ){
			return "redirect:/jsp/login?result="+GlobalValues.MSG_PLEASE_LOGIN;
		} else {
			System.out.println(examType+" "+level+" " + selectedIds );
			Serializable ret = null;
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
			model.addAttribute("questions", questionInfos);
			model.addAttribute("examType", examType);
			return "startExam";
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
		System.out.println("user="+user+"answerLogs="+answerLogs);
		if ( user == null ){
			return "redirect:/jsp/login?result="+GlobalValues.MSG_PLEASE_LOGIN;
		} else {
//			ObjectMapper mapper = new ObjectMapper();
//			List<UserAnswerLogInfo> result = mapper.readValue(answerLogs, TypeFactory.collectionType(ArrayList.class, UserAnswerLogInfo.class));
//			List<String> logList = ListUtil.stringsToListSplitBy(answerLogs, ",");
//			if ( ListUtil.isNotEmpty(logList) ){
//				for ( String log : logList ){
//					
//				}
//			}
			QuestionInfo quest = new QuestionInfo();
			int i = 100;
			quest.setId(i);
			quest.setAnswer("C");
			quest.setLevel("一般");
			return quest;
		}
	}
	
	/**结束考试
	 * @return
	 */
	public Serializable endExam(){
		return "";
	}
}
