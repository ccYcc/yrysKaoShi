package com.ccc.test.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ccc.test.exception.SimpleHandleException;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.PaperInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.pojo.json.JsPaperWrapper;
import com.ccc.test.service.interfaces.IQuestionService;
import com.ccc.test.service.interfaces.ITeacherService;
import com.ccc.test.service.interfaces.IUserService;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.FileUtil;
import com.ccc.test.utils.GlobalValues;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//代表控制层
@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	IUserService userService;
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
			String paperStr,ModelMap model,HttpServletRequest request){
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
					String question_ids = null;
					MsgInfo msg = new MsgInfo();
					try {
						msg = (MsgInfo) questService.uploadPaperQuest(questionsList);
						msg = (MsgInfo) questService.uploadQuestKnowledge(questionsList);
						StringBuffer sb = new StringBuffer();
						for ( QuestionInfo quest : questionsList ){
							sb.append(quest.getId()).append(",");
						}
						question_ids = sb.substring(0, sb.length()-1);
						teacherService.uploadPaper(filePath, paperName, question_ids, cur.getId());
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
		return "uploadPaper";
	}
	
	@RequestMapping(value = "/service/uploadPhoto",method = RequestMethod.POST)
	public Serializable uploadUserPhoto(MultipartFile file,ModelMap model,HttpSession session){
		UserInfo cur = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		Serializable ret = FileUtil.saveFile(session, file, FileUtil.CATEGORY_PHOTO,cur.getId()+"");
		if ( ret instanceof String){
			String retFilePath = (String) ret;
			cur.setHeadUrl(retFilePath);
			try {
				Serializable retuser = userService.updateUserInfo(cur);
				if ( retuser instanceof UserInfo ){
					session.setAttribute(GlobalValues.SESSION_USER, retuser);
				} else {
					model.addAttribute("result",retuser);
				}
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("result","更新失败");
			}
		} else {
			model.addAttribute("result","更新失败");
		}
		return "redirect:/jsp/editUser";
	}
}
