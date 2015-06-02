package com.ccc.test.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ccc.test.exception.SimpleHandleException;
import com.ccc.test.pojo.GroupInfo;
import com.ccc.test.pojo.PaperInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.service.interfaces.IGroupService;
import com.ccc.test.service.interfaces.ITeacherService;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;

@Controller
@RequestMapping("/jsp")
public class JspPageController {  

	@Autowired
	ITeacherService teacherService;
	@Autowired
	IGroupService groupService;
	SimpleHandleException simpleHandleException = new SimpleHandleException();
	
	@RequestMapping("/login")
	public String toLogin(){ 
		System.out.println("toLogin");
		return "login";
	}
	
	@RequestMapping("/toAllUserPage")
	public String toAddUser(){
		System.out.println("toAddUser");
			return "addUser";
	}
	@RequestMapping("/toStudentMain")
	public String toStudentMain2(HttpSession session){
		return "studentMain";
	}
	@RequestMapping("/toTeacherMain")
	public String teacherMain(HttpSession session){
			return "teacherMain";
	}
	@RequestMapping("/teacherClass")
	public String teacherClass(HttpSession session){
			return "teacherClass";
	}
	@RequestMapping("/toAdminMain")
	public String toAdminMain(HttpSession session){
			return "adminMain";
	}
	@RequestMapping("/toUploadFile")
	public String toUploadFile(){
			return "uploadfile";
	}
	@RequestMapping("/toUploadPaper")
	public String toUploadPaper(HttpSession session,ModelMap model){
			UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
			if ( user != null ){
				try {
					Serializable ret = groupService.QueryGroups(user.getId(), 0);
					List<GroupInfo> groups = new ArrayList<GroupInfo>();
					if ( ret instanceof List ){
						groups = (List<GroupInfo>) ret;
					} else {
						model.addAttribute("result",ret);
					}
					model.addAttribute("groups",groups);
				} catch (Exception e) {
					e.printStackTrace();
					simpleHandleException.handle(e, model);
				}
			}
			return "uploadPaper";
	}
	
	/**访问老师的班级页面
	 * @param session
	 * @param model
	 * @param id  班级id，如果为null  或者 在班级列表中找不到则使用列表中的第一个班级的id
	 * @return
	 */
	@RequestMapping("/toTeacherClass")
	public String toTeacherClass(HttpSession session,ModelMap model,RedirectAttributes raModel,String id){
			UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
			if ( user != null && "老师".equals(user.getType()) ){
				try {
					int gid = 0;
					try {
						gid = Integer.valueOf(id);
					} catch (Exception e) {
					}
					Serializable ret = groupService.QueryGroups(user.getId(), 0);
					List<PaperInfo> papersInGroup = null;
					List<UserInfo> stusInGroup = null;
					
					if ( ret instanceof List ){
						List<GroupInfo> groups = (List<GroupInfo>) ret;
						GroupInfo thisGroup = null;//请求的group
						if (  ListUtil.isNotEmpty(groups)  ){
							for ( GroupInfo g : groups ){
								if ( gid == g.getId() ){
									thisGroup = g;
									break;
								}
							}
							thisGroup = thisGroup == null ? groups.get(0):thisGroup;
							gid = thisGroup.getId();
							Serializable sturet = groupService.queryStuList(gid);
							if ( sturet instanceof List ){
								stusInGroup = (List<UserInfo>) sturet;
							} else {
								model.addAttribute("result",sturet);
							}
							Serializable retpp = groupService.fetchPaper(gid);
							if ( retpp instanceof List ){
								papersInGroup = (List<PaperInfo>) retpp;
							} else {
								model.addAttribute("result",retpp);
							}
						} else {//如果老师没有班级
							gid = 0;
						}
						model.addAttribute("groupId",gid);
						model.addAttribute("groups",groups);
						model.addAttribute("papers",papersInGroup);
						model.addAttribute("students",stusInGroup);
					} else {
						model.addAttribute("result",ret);
					}
				} catch (Exception e) {
					e.printStackTrace();
					simpleHandleException.handle(e, model);
				}
			} else {
				model.addAttribute("result",GlobalValues.MSG_PLEASE_LOGIN);
				simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
				return "redirect:/jsp/login";
			}
			return "teacherClass";
	}
	
	/**访问学生的班级页面
	 * @param session
	 * @param model
	 * @param id  班级id，如果为null  或者 在班级列表中找不到则使用列表中的第一个班级的id
	 * @return
	 */
	@RequestMapping("/toStudentClass")
	public String toStudentClass(HttpSession session,ModelMap model,RedirectAttributes raModel,String id){
			UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
			if ( user != null && "学生".equals(user.getType()) ){
				try {
					int gid = 0;
					try {
						gid = Integer.valueOf(id);
					} catch (Exception e) {
					}
					Serializable ret = groupService.QueryGroups(user.getId(), 1);
					List<PaperInfo> papersInGroup = null;
//					List<UserInfo> stusInGroup = null;
					if ( ret instanceof List ){
						List<GroupInfo> groups = (List<GroupInfo>) ret;
						GroupInfo thisGroup = null;//请求的group
						if (  ListUtil.isNotEmpty(groups)  ){
							for ( GroupInfo g : groups ){
								if ( gid == g.getId() ){
									thisGroup = g;
									break;
								}
							}
							thisGroup = thisGroup == null ? groups.get(0):thisGroup;
							gid = thisGroup.getId();
//							Serializable sturet = groupService.queryStuList(gid);
//							if ( sturet instanceof List ){
//								stusInGroup = (List<UserInfo>) sturet;
//							} else {
//								model.addAttribute("result",sturet);
//							}
							Serializable retpp = groupService.fetchPaper(gid);
							if ( retpp instanceof List ){
								papersInGroup = (List<PaperInfo>) retpp;
							} else {
								model.addAttribute("result",retpp);
							}
						} else {//如果没有班级
							gid = 0;
						}
						model.addAttribute("groupId",gid);
						model.addAttribute("groups",groups);
						model.addAttribute("papers",papersInGroup);
//						model.addAttribute("students",stusInGroup);
					} else {
						model.addAttribute("result",ret);
					}
				} catch (Exception e) {
					e.printStackTrace();
					simpleHandleException.handle(e, model);
				}
			} else {
				model.addAttribute("result",GlobalValues.MSG_PLEASE_LOGIN);
				simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
				return "redirect:/jsp/login";
			}
			return "studentClass";
	}
	
	@RequestMapping("/startExam")
	public String toStartTest(){
			return "startExam";
	}
	@RequestMapping("/editUser")
	public String editUser(){
			return "editUser";
	}
}
