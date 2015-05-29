package com.ccc.test.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ccc.test.exception.SimpleHandleException;
import com.ccc.test.pojo.GroupInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.PaperInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.service.interfaces.IGroupService;
import com.ccc.test.service.interfaces.IQuestionService;
import com.ccc.test.service.interfaces.ITeacherService;
import com.ccc.test.service.interfaces.IUserService;
import com.ccc.test.utils.FileUtil;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

//代表控制层
@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	IUserService userService;
	@Autowired
	IGroupService groupService;
	SimpleHandleException simpleHandleException = new SimpleHandleException();
	
	@Autowired
	ITeacherService teacherService;
	@Autowired
	IQuestionService questService;
	
	/**uploadPaper 上传试卷
	 * @param searchText
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/uploadPaper",method = RequestMethod.POST)
	public Serializable uploadPaper(
			MultipartFile file,
			String paperStr,
			String clazzIds,ModelMap model,HttpServletRequest request,RedirectAttributes raModel){
		HttpSession httpSession = request.getSession();
		UserInfo cur = (UserInfo) httpSession.getAttribute(GlobalValues.SESSION_USER);
		PaperInfo paper = null;
		ObjectMapper mapper = new ObjectMapper();

		if ( cur != null && "老师".equals(cur.getType())) {
			try {
				paper = mapper.readValue(paperStr, PaperInfo.class);
				Serializable saveret = FileUtil.saveFile(httpSession, file, FileUtil.CATEGORY_PAPERS, null);
				if ( saveret instanceof String ){
					String filePath = (String) saveret;
					String paperName = FileUtil.getNameByPath(paper.getName());
					List<QuestionInfo> questionsList = paper.getQuestions();
					List<Integer> questIdsList = new ArrayList<Integer>();
					if ( ListUtil.isNotEmpty(questionsList) ){
						for ( QuestionInfo info : questionsList ){
							questIdsList.add(info.getId());
						}
					}
					String question_ids = ListUtil.listToStringJoinBySplit(questIdsList, ",");
					MsgInfo msg = new MsgInfo();
					try {
						msg = (MsgInfo) questService.uploadPaperQuest(questionsList);
						msg = (MsgInfo) questService.uploadQuestKnowledge(questionsList);
						teacherService.uploadPaper(filePath, paperName, question_ids, cur.getId(), clazzIds);
						model.addAttribute("result",msg.toString());
					} catch (Exception e) {
						e.printStackTrace();
						simpleHandleException.handle(e, model);
					}
				} else {
					model.addAttribute("result",GlobalValues.MSG_ADD_FAILED);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				simpleHandleException.handle(e1, model);
			}
		} else {
			model.addAttribute("result","没有权限操作");
		}
		raModel.addFlashAttribute("result", model.get("result"));
		return "redirect:/jsp/toTeacherMain";
	}
	
	@ResponseBody
	@RequestMapping("/json/createGroup")
	public Serializable createGroup(String clazz,HttpServletRequest request){
		UserInfo cur = (UserInfo) request.getSession().getAttribute(GlobalValues.SESSION_USER);
		ObjectMapper mapper = new ObjectMapper();
		if ( cur != null && "老师".equals(cur.getType())) {
			try {
				GroupInfo group  = mapper.readValue(clazz, GroupInfo.class);
				Serializable ret = teacherService.create_group(cur.getId(), group.getName(),group.getDescription());
				if ( ret instanceof Integer ){
					int id = (Integer) ret;
					if ( id > 0 ){
						return id;
					}
				}
			} catch (Exception e) {
			}
		} else {
			
		}
		return 0;
	}
	
	@ResponseBody
	@RequestMapping("/json/updateGroup")
	public Serializable updateGroup(String clazz,HttpServletRequest request){
		UserInfo cur = (UserInfo) request.getSession().getAttribute(GlobalValues.SESSION_USER);
		ObjectMapper mapper = new ObjectMapper();
		if ( cur != null && "老师".equals(cur.getType())) {
			try {
				GroupInfo group  = mapper.readValue(clazz, GroupInfo.class);
				MsgInfo msg= (MsgInfo) groupService.updateGroup(group);
				if ( GlobalValues.CODE_UPDATE_SUCCESS == msg.getCode() ){
					return group.getId();
				}
			} catch (Exception e) {
			}
		} else {
			
		}
		return false;
	}
	
	@ResponseBody
	@RequestMapping("/json/deleteGroup")
	public Serializable deleteGroup(String clazz,HttpServletRequest request){
		UserInfo cur = (UserInfo) request.getSession().getAttribute(GlobalValues.SESSION_USER);
		ObjectMapper mapper = new ObjectMapper();
		if ( cur != null && "老师".equals(cur.getType())) {
			try {
				GroupInfo group  = mapper.readValue(clazz, GroupInfo.class);
				MsgInfo msg= (MsgInfo) groupService.deleteGroup(group.getId());
				if ( GlobalValues.CODE_DELETE_SUCCESS == msg.getCode() ){
					return true;
				}
			} catch (Exception e) {
			}
		} else {
			
		}
		return false;
	}
	
	public Serializable handleGroupRequest(){
		return null;
	}
	
	public Serializable viewGroups(){
		return null;
	}
	
}
