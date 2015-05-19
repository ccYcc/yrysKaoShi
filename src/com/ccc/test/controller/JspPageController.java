package com.ccc.test.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccc.test.pojo.UserInfo;
import com.ccc.test.utils.GlobalValues;

@Controller
@RequestMapping("/jsp")
public class JspPageController {

	@RequestMapping("/main")
	public String toMainPage(){ 
		System.out.println("toMainPage");
		return "main";
	}
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
	public String toStudentMain(){
			return "studentMain";
	}
	@RequestMapping("/toStudentMain2")
	public String toStudentMain2(){
			return "studentMain2";
	}
	@RequestMapping("/toAdminMain")
	public String toAdminMain(){
			return "adminMain";
	}
	@RequestMapping("/toUploadFile")
	public String toUploadFile(){
			return "uploadfile";
	}
	
	@RequestMapping("/startExam")
	public String toStartTest(){
			return "startExam";
	}
}
