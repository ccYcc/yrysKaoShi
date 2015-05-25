package com.ccc.test.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.ccc.test.exception.SimpleHandleException;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.pojo.json.JsPaperWrapper;
import com.ccc.test.service.interfaces.ITeacherService;
import com.ccc.test.service.interfaces.IUserService;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.FileUtil;
import com.ccc.test.utils.GlobalValues;

//代表控制层
@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	IUserService userService;
	SimpleHandleException simpleHandleException = new SimpleHandleException();
	
	@Autowired
	ITeacherService teacherService;
	
	
	/**uploadPaper 上传试卷
	 * @param searchText
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/uploadPaper",method = RequestMethod.POST)
	public Serializable uploadPaper(MultipartFile file,JsPaperWrapper paper,ModelMap model,HttpSession httpSession){
		UserInfo cur = (UserInfo) httpSession.getAttribute(GlobalValues.SESSION_USER);
		return "searchUserResults";
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
