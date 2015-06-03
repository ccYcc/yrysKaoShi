package com.ccc.test.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ccc.test.exception.SimpleHandleException;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.service.interfaces.IGroupService;
import com.ccc.test.service.interfaces.IQuestionService;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**考试控制器
 * @author Trible Chen
 *
 */
@Controller
@RequestMapping("/exam")
public class ExamController {

	@Autowired
	IQuestionService questService;
	
	@Autowired
	IGroupService groupService;
	
	SimpleHandleException simpleHandleException = new SimpleHandleException();
	
	/**测试前选择知识点
	 * @param examType 测试类型，如练习本，自主测试
	 * @param model
	 * @return
	 */
	@RequestMapping("type/{examType}")
	public String toChooseKnowledge(@PathVariable String examType,ModelMap model){
			model.addAttribute("examType", examType);
			return "chooseKnowledge";
	}
	
	/**开始考试接口
	 * @param examType 考试类型
	 * @param level 考试难度
	 * @param selectedIds 选择考试的知识点
	 * @param answerType 答题方式，快速或者正常答题
	 * @param paperName 自定义试卷名字
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/startExam",method = RequestMethod.POST)
	public Serializable startExam(String examType,String level,String selectedIds,String answerType,String paperName,
			ModelMap  model,
			HttpSession session,
			RedirectAttributes raModel){
		UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		if ( user == null ){
			model.addAttribute("result",GlobalValues.MSG_PLEASE_LOGIN);
			simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
			return "redirect:/jsp/login";
		} else {
			model.addAttribute("examType", examType);
			model.addAttribute("level", level);
			model.addAttribute("selectedIds", selectedIds);
			model.addAttribute("answerType", answerType);
			model.addAttribute("paperName", paperName);
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
			ModelMap model,
			RedirectAttributes raModel){
			UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
			if ( user == null ){
				model.addAttribute("result",GlobalValues.MSG_PLEASE_LOGIN);
				simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
				return "redirect:/jsp/login";
			} else {
				Bog.print("fetchExerciseQuestions:level="+level+" ids=" + selectedIds );
				List<Integer> idsnum = ListUtil.stringsToTListSplitBy(selectedIds, ",");
				List<QuestionInfo> questionInfos =new ArrayList<QuestionInfo>();
				ObjectMapper mapper = new ObjectMapper();
				try {
					questionInfos = (List<QuestionInfo>) questService.getQuestionsByRandom(idsnum, level, 20);
					return mapper.writeValueAsString(questionInfos);
				} catch (Exception e) {
					e.printStackTrace();
					return "startExam";
				}
			}
	}
	
	/**下一道题       用于自主测试
	 * @param answerLogs 回答的记录。格式为：qid:ansResult:usedTime,qid:ansResult:usedTime...
	 * @param session
	 * @param model
	 * @return 如果是null代表没有下一道题了。
	 */
	@RequestMapping(value = "/json/nextQuestion",method = RequestMethod.POST)
	@ResponseBody
	public Serializable nextQuestion(String answerLogs,String level,String selectedIds,
			HttpSession session,
			ModelMap model,
			RedirectAttributes raModel){
		
		UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		if ( user == null ){
			model.addAttribute("result",GlobalValues.MSG_PLEASE_LOGIN);
			simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
			return "redirect:/jsp/login";
		} else {
			List<Integer> idsnum = ListUtil.stringsToTListSplitBy(selectedIds, ",");
//			List<UserAnswerLogInfo> result = ListUtil.jsonArrToList(answerLogs, new TypeReference<List<UserAnswerLogInfo>>() {});
//			questServie.getOneQuestionsByMethod(knowledges, level)
			QuestionInfo quest = new QuestionInfo();
			int i = (int)(Math.random()*100);
			quest.setId(i);
			quest.setOptions("A;B;C;D;E");
			quest.setAnswer("B;E");
			quest.setLevel("一般");
			return quest;
		}
	}
	
	@RequestMapping("/fetchQuestionInPaper")
	public String fetchQuestionInPaper(
			HttpSession session,ModelMap model,RedirectAttributes raModel,
			String pid){
		
		UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		if ( user == null ){
			model.addAttribute("result",GlobalValues.MSG_PLEASE_LOGIN);
			simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
			return "redirect:/jsp/login";
		} else {
			int paperId = 0;
			try {
				paperId = Integer.valueOf(pid);
			} catch (Exception e) {
			}
			List<QuestionInfo> questions = new ArrayList<QuestionInfo>();
			Serializable ret = groupService.fetchQuestions(paperId);
			if ( ret instanceof List ){
				questions = (List<QuestionInfo>) ret;
				model.addAttribute("questions",questions);
			} else {
				model.addAttribute("result", ret);
			}
		}
		return "examInPaper";
	}
	/**结束考试
	 * @param answerLogs 回答记录
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/endExam",method = RequestMethod.POST)
	public Serializable endExam(
			String answerLogs,
			String selectKnldgIds,
			String examType,
			String paperName,
			HttpSession session,
			ModelMap model){
		Bog.print(answerLogs);
		List<UserAnswerLogInfo> result = ListUtil.jsonArrToList(answerLogs, new TypeReference<List<UserAnswerLogInfo>>() {});
		return "";
	}
	
}
