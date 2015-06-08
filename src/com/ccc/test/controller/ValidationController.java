package com.ccc.test.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ccc.test.exception.SimpleHandleException;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.pojo.ValidtionInfo;
import com.ccc.test.service.interfaces.IGroupService;
import com.ccc.test.service.interfaces.ITeacherService;
import com.ccc.test.service.interfaces.IUserService;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.StringUtil;
import com.ccc.test.utils.UtilDao;

@Controller
@RequestMapping("/validations")
public class ValidationController {
	
	@Autowired
	IUserService userService;
	@Autowired
	IGroupService groupService;
	@Autowired
	ITeacherService teacherService;
	
	SimpleHandleException simpleHandleException = new SimpleHandleException();
	
	@RequestMapping(value = "/getValidations",method = {RequestMethod.GET,RequestMethod.POST})
	public Serializable getValidations(ModelMap  model,
			HttpSession session,
			RedirectAttributes raModel){
		UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		List<ValidtionInfo> results = new ArrayList<ValidtionInfo>();
		if ( user == null ){
			model.addAttribute("result",GlobalValues.MSG_PLEASE_LOGIN);
			simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
			return "redirect:/jsp/login";
		} else {
			Serializable ret = groupService.hasNewInfo(user.getId());
			if ( ret instanceof List ){
				results = (List<ValidtionInfo>) ret;
			} else {
				model.addAttribute("result",ret);
			}
		}
		model.addAttribute("messages",results);
		return "message";
	}
	@RequestMapping(value = "/handleActions",method = {RequestMethod.GET,RequestMethod.POST} )
	public Serializable handleValidations(ModelMap  model,
			HttpSession session,
			RedirectAttributes raModel,
			ValidtionInfo vInfo,
			String action){
		
		UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		List<ValidtionInfo> results = new ArrayList<ValidtionInfo>();
		if ( user == null ){
			model.addAttribute("result",GlobalValues.MSG_PLEASE_LOGIN);
			simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
			return "redirect:/jsp/login";
		} else {
			try{
				MsgInfo msg = null;
				if ( "agree".equals(action) ){
					String message = "老师已经同意你加入班级";
					UserInfo teacher = UtilDao.getById(new UserInfo(), vInfo.getAccept_id());
					if ( teacher != null && !StringUtil.isEmpty(teacher.getUsername()) ){
						message = teacher.getUsername()+message;
					}
					msg = (MsgInfo) teacherService.handleRequest(vInfo.getGroupId(), vInfo.getRequest_id(), vInfo.getAccept_id(), message, 1);
				} else if ("delete".equals(action) ||  "reject".equals(action) ){
					msg = (MsgInfo) userService.deleteValidate(vInfo.getRequest_id(), vInfo.getGroupId());
				}
				if ( (!StringUtil.isEmpty(msg.getMessage()) && !msg.getMessage().contains("成功")) ){
					model.addAttribute("result","操作失败，请重试");
				}
			}catch (Exception e) {
				simpleHandleException.handle(e, model);
			}
			simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
		}
		return "redirect:getValidations";
	}
}
