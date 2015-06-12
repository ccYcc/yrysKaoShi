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
import com.ccc.test.pojo.DiyPaperInfo;
import com.ccc.test.pojo.GroupInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.TeacherPaperInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.service.interfaces.IAlgorithmService;
import com.ccc.test.service.interfaces.IDiyPaperService;
import com.ccc.test.service.interfaces.IGroupService;
import com.ccc.test.service.interfaces.IQuestionService;
import com.ccc.test.service.interfaces.IUserService;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;
import com.ccc.test.utils.PropertiesUtil;
import com.ccc.test.utils.StringUtil;
import com.ccc.test.utils.TimeUtil;
import com.ccc.test.utils.UtilDao;
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
	IUserService userService;
	@Autowired
	IGroupService groupService;
	@Autowired
	IDiyPaperService diyPaperService;
	@Autowired
	IAlgorithmService algorithmService;
	
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
	 * @param paperName 自定义试卷名字 如果为空则用默认格式：难度_时间
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
			if ( StringUtil.isEmpty(paperName) ){
				paperName = "测试_"+level +"_"+TimeUtil.format(System.currentTimeMillis(), "yyyy-MM-dd  hh:mm");
			}
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
	@SuppressWarnings("unchecked")
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
	public Serializable nextQuestion(
			String answerLogs,
			String level,
			String selectedIds,
			HttpSession session,
			ModelMap model,
			RedirectAttributes raModel){
		
		UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		if ( user == null ){
			model.addAttribute("result",GlobalValues.MSG_PLEASE_LOGIN);
			simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
			return "redirect:/jsp/login";
		} else {
			try{
				List<Integer> idsnum = ListUtil.stringsToTListSplitBy(selectedIds, ",");
				List<UserAnswerLogInfo> result = ListUtil.jsonArrToList(answerLogs, new TypeReference<List<UserAnswerLogInfo>>() {});
				Serializable ret =  questService.getOneQuestionsByMethod(idsnum, level, result);
				QuestionInfo quest = null;
				if ( ret instanceof List ){
					List questret  = (List) ret;
					if ( questret.size() > 0 ){
						quest = (QuestionInfo) questret.get(0);
					}
				}
				return quest;
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	@RequestMapping(value = "/fetchQuestionInPaper",method = {RequestMethod.POST,RequestMethod.GET})
	public String fetchQuestionInPaper(
			HttpSession session,ModelMap model,RedirectAttributes raModel,
			String pid,String tid,String gid){
		
		UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		if ( user == null ){
			model.addAttribute("result",GlobalValues.MSG_PLEASE_LOGIN);
			simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
			return "redirect:/jsp/login";
		} else {
			int paperId = 0,teacherId = 0,groupId = 0;
			try {
				paperId = Integer.valueOf(pid);
				teacherId = Integer.valueOf(tid);
				groupId = Integer.valueOf(gid);
			} catch (Exception e) {
			}
			List<QuestionInfo> questions = new ArrayList<QuestionInfo>();
			Serializable ret = groupService.fetchQuestions(paperId);
			Serializable pret = groupService.fetchPaperById(paperId);
			try {
				Serializable tret = UtilDao.getById(new UserInfo(), teacherId);
				if ( tret instanceof UserInfo ){
					model.addAttribute("teacher",tret);
				} else {
					model.addAttribute("result",tret);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Serializable gret = groupService.queryGroup(groupId);
			if ( gret instanceof GroupInfo ){
				model.addAttribute("group",gret);
			} else {
				model.addAttribute("result",gret);
			}
			if ( pret instanceof TeacherPaperInfo ){
				model.addAttribute("teacherPaper",pret);
			} else {
				model.addAttribute("result",pret);
			}
			if ( ret instanceof List ){
				questions = (List<QuestionInfo>) ret;
				model.addAttribute("questions",questions);
			} else {
				model.addAttribute("result", ret);
			}
		}
		return "examInPaper";
	}
	
	/**
	 * @param session
	 * @param model
	 * @param raModel
	 * @param uid 学生id
	 * @return
	 */
	@RequestMapping(value = "/history",method = {RequestMethod.POST,RequestMethod.GET})
	public Serializable examHistory(HttpSession session,ModelMap model,RedirectAttributes raModel,Integer uid){
		UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		if ( user == null ){
			model.addAttribute("result",GlobalValues.MSG_PLEASE_LOGIN);
			simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
			return "redirect:/jsp/login";
		} else {
			try {
				UserInfo student = null;
				if ( uid == null ) {
					student = user;
					uid = user.getId();
				} else {
					student = UtilDao.getById(new UserInfo(), uid);
				}
				List<DiyPaperInfo> divPapers = new ArrayList<DiyPaperInfo>();
				Serializable pret = diyPaperService.fetchUserPaperList(uid);
				if ( pret instanceof List ){
					divPapers =  (List<DiyPaperInfo>) pret;
				} else {
					model.addAttribute("result", pret);
				}
				model.addAttribute("historys", divPapers);
				model.addAttribute("student",student);
			} catch (Exception e) {
				e.printStackTrace();
				simpleHandleException.handle(e, model);
			}
		}
		return "examHistory";
	}
	/**
	 * @param session
	 * @param model
	 * @param raModel
	 * @param hid 具体试卷id
	 * @return
	 */
	@RequestMapping(value = "/historyDetail",method = {RequestMethod.POST,RequestMethod.GET})
	public Serializable examHistoryDetail(HttpSession session,ModelMap model,RedirectAttributes raModel,Integer hid,Integer uid){
		UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		if ( user == null ){
			model.addAttribute("result",GlobalValues.MSG_PLEASE_LOGIN);
			simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
			return "redirect:/jsp/login";
		} else {
			try{
				DiyPaperInfo paper = new DiyPaperInfo();
				Serializable ret = diyPaperService.fetchUserPaper(hid);
				Serializable student = UtilDao.getById(new UserInfo(), uid);
				if ( ret instanceof DiyPaperInfo){
					paper = (DiyPaperInfo) ret;
				} else {
					model.addAttribute("result",ret);
				}
				model.addAttribute("detailPaper",paper);
				model.addAttribute("student",student);
			}catch (Exception e) {
				e.printStackTrace();
				simpleHandleException.handle(e, model);
			}
		}
		simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
		return "examHistoryDetail";
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
			String selectedIds,
			String examType,
			String paperName,
			String level,
			HttpSession session,
			ModelMap model,
			RedirectAttributes raModel){
		UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		if ( user == null ){
			model.addAttribute("result",GlobalValues.MSG_PLEASE_LOGIN);
			simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
			return "redirect:/jsp/login";
		} else {
			try{
				List<UserAnswerLogInfo> ansLogresult = ListUtil.jsonArrToList(answerLogs, new TypeReference<List<UserAnswerLogInfo>>() {});
				MsgInfo logsMsg = (MsgInfo) diyPaperService.writeAnsLogs(ansLogresult);
				if ( logsMsg.getCode() == GlobalValues.CODE_SUCCESS ){
					MsgInfo questMsg = (MsgInfo) diyPaperService.updateQuestion(ansLogresult);
					if ( questMsg.getCode() == GlobalValues.CODE_SUCCESS ){
						List<Integer> SelectKnoledgesID = ListUtil.stringsToTListSplitBy(selectedIds, ",");
						String goodbadKnowledges = (String) algorithmService.CheckUserGoodBadKnowledges(
								ansLogresult, SelectKnoledgesID);
						String goodKnowledges = null,badKnowledges = null;
						
						String learnLevel = GlobalValues.learnLevelMapper.get(algorithmService.GetLearnLevel(ansLogresult));
						if ( !StringUtil.isEmpty(goodbadKnowledges) ){
							String[] knowledges = goodbadKnowledges.split(";");
							if ( knowledges != null && knowledges.length >= 2 ){
								goodKnowledges = knowledges[0];
								badKnowledges = knowledges[1];
							}
						}
						List<Integer> badKnoledgesList = ListUtil.stringsToTListSplitBy(badKnowledges, ",");
						String recommendsQuestions = (String) algorithmService.GetRecommendsQuestions(
								SelectKnoledgesID, ansLogresult, badKnoledgesList, level,
								Integer.valueOf(PropertiesUtil.getString("RecommendQuestionNum")));
						Serializable cret = diyPaperService.createPaper(selectedIds, paperName, ansLogresult, user.getId(), level, learnLevel, goodKnowledges, badKnowledges, null, recommendsQuestions);
						if ( cret instanceof Integer &&  (Integer) cret > 0  ){
							//提交测试记录成功
							raModel.addAttribute("hid", cret);
							raModel.addAttribute("uid", user.getId());
							return "redirect:historyDetail";
						} else {
							model.addAttribute("result",cret);
						}
					} else {
						model.addAttribute("result",questMsg);
					}
				} else {
					model.addAttribute("result",logsMsg);
				}
			} catch (Exception e) {
				e.printStackTrace();
				simpleHandleException.handle(e, model);
			}
			
		}
		simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
		return "redirect:historyDetail";
	}
	
}
