package com.ccc.test.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ccc.test.pojo.UserInfo;
import com.ccc.test.service.interfaces.IDeleteService;
import com.ccc.test.service.interfaces.IDiyPaperService;
import com.ccc.test.service.interfaces.IGroupService;
import com.ccc.test.service.interfaces.IQuestionService;
import com.ccc.test.service.interfaces.ITeacherService;
import com.ccc.test.service.interfaces.IUserService;
import com.ccc.test.service.interfaces.IUserStatisticService;
import com.ccc.test.utils.GlobalValues;

@Controller
@RequestMapping("/delete")
public class DeleteController {

		@Autowired
		IDeleteService deleteService;
		
		@RequestMapping(value = "/deleteData.do",method = RequestMethod.POST)
		public String DeleteQuestions(HttpSession httpSession,String delete_select,ModelMap model){
			
			UserInfo user_obj=(UserInfo) httpSession.getAttribute(GlobalValues.SESSION_USER);
			if(user_obj==null||!StringUtils.equals(user_obj.getType(), "管理员"))
				return "redirect:/jsp/login.do";
			
			if(StringUtils.equals(delete_select, "deleteQuestions"))
				deleteService.DeleteAllQuestions();
			else if(StringUtils.equals(delete_select, "deleteKnowledages"))
				deleteService.DeleteAllKnowledages();
			else if(StringUtils.equals(delete_select, "deleteUsers"))
				deleteService.DeleteAllUserDatas();
			else if(StringUtils.equals(delete_select, "deleteAll")){
				deleteService.DeleteAllUserDatas();
				deleteService.DeleteAllQuestions();
				deleteService.DeleteAllKnowledages();
			}
			return "redirect:/jsp/toAdminMain";
		}
}
