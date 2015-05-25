package com.ccc.test.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccc.test.pojo.UserInfo;
import com.ccc.test.utils.GlobalValues;

@Controller
@RequestMapping("/jsp")
public class JspPageController {

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
	@RequestMapping("/teacherMain")
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
	
	@RequestMapping("/startExam")
	public String toStartTest(){
			return "startExam";
	}
	@RequestMapping("/editUser")
	public String editUser(){
			return "editUser";
	}
}
