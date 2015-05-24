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

import com.ccc.test.pojo.UserInfo;
import com.ccc.test.pojo.ValidtionInfo;
import com.ccc.test.service.interfaces.IUserService;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.GlobalValues;

@Controller
@RequestMapping("/validations")
public class ValidationController {
	
	@Autowired
	IUserService userService;
	
	@RequestMapping(value = "/getValidations",method = {RequestMethod.GET,RequestMethod.POST})
	public Serializable getValidations(ModelMap  model,
			HttpSession session){
		UserInfo user = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		Bog.print("user="+user);
		List<ValidtionInfo> results = new ArrayList<ValidtionInfo>();
		if ( user != null ){
		
		} else {
			
		}
		int num = 10;
		for ( int i = 0 ; i < num ; i++ ){
			ValidtionInfo valid = new ValidtionInfo();
			valid.setAccept_id(i);
			valid.setRequest_id(i);
			valid.setMessage("message=+"+i);
			valid.setId(user.getId());
			results.add(valid);
		}
		model.addAttribute("results",results);
		return "message";
	}
}
