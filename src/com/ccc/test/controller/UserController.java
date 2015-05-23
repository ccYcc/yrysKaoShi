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
import com.ccc.test.service.interfaces.IUserService;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.FileUtil;
import com.ccc.test.utils.GlobalValues;

//代表控制层
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	IUserService userService;
	SimpleHandleException simpleHandleException = new SimpleHandleException();
	
	/**用户注册时调用
	 * @param username
	 * @param password
	 * @param con_password
	 * @param usertype
	 * @param model
	 * @return 返回页面main.jsp
	 */
	@RequestMapping(value = "/register.do",method = RequestMethod.POST)
	public String register(
			String username,//参数名字与jsp传递的参数名字一致
			String password,
			String con_password,
			String usertype,
			ModelMap model){
		
			try {
				Serializable ret = userService.register(username, password, con_password, usertype);
				if( ret instanceof MsgInfo ){
					MsgInfo msg = (MsgInfo) ret;
					model.addAttribute("result",msg.toString());
				} else {
					model.addAttribute("result","注册过程出现错误");
				}
			} catch (Exception e) {
				simpleHandleException.handle(e, model);
			}
			return "login";
	}
	
	
	/**用户登录时调用
	 * @param username
	 * @param password
	 * @param usertype
	 * @param model 用于保存返回的数据，这里是保存用户对象
	 * @return 返回页面名字字符串，如main.jsp页面对应的字符串是main
	 */
	@RequestMapping(value = "/login.do",method = RequestMethod.POST)
	public String login(
			String username,
			String password,
			String usertype,
			ModelMap model,
			HttpSession httpSession){
		
			try {
				String tokenid = userService.login(username, password,usertype);
				Serializable ret = userService.fetchUserInfo(tokenid);
				if ( ret instanceof UserInfo ){
					UserInfo user = (UserInfo) ret;
					//addAttribute 值不能为空
					String type = user.getType();
					httpSession.setAttribute(GlobalValues.SESSION_USER,user);
					if ( "学生".equals(type) ){
						return "redirect:/jsp/toStudentMain2";
					} else if ( "老师".equals(type) ){
						
					} else if ( "管理员".equals(type ) ){
						return "redirect:/jsp/toAdminMain";
					}
					model.addAttribute("result", type+" 角色的主页还没实现！");
				} else if ( ret instanceof MsgInfo ){
					MsgInfo msg = (MsgInfo) ret;
					model.addAttribute("result",msg.toString());
				}
			} catch (Exception e) {
				simpleHandleException.handle(e, model);
			}
		return "login";
	}
	
	/**搜索接口
	 * @param searchText
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/service/search",method = RequestMethod.POST)
	public Serializable search(String searchText,ModelMap model){
		Bog.print("search="+searchText);
		List<UserInfo> users = new ArrayList<UserInfo>();
		int c = 10;
		for ( int i = 0 ; i < c ; i++){
			double r = Math.random();
			UserInfo user = new UserInfo();
			user.setUsername(searchText+"-"+i);
			user.setDescription("description"+r);
			users.add(user);
		}
		model.addAttribute("results", users);
		return "searchUserResults";
	}
	
	@RequestMapping(value = "/service/update",method = RequestMethod.POST)
	public Serializable update(UserInfo newUser,ModelMap model,HttpSession httpSession){
		UserInfo cur = (UserInfo) httpSession.getAttribute(GlobalValues.SESSION_USER);
		if ( cur.getId() == newUser.getId() ){
			try {
				Serializable ret = userService.updateUserInfo(newUser);
				if ( ret instanceof MsgInfo ){
					model.addAttribute("result",ret.toString());
				} else if ( ret instanceof UserInfo ){
					model.addAttribute("result","更新成功");
					httpSession.setAttribute(GlobalValues.SESSION_USER, ret);
				}
			} catch (Exception e) {
				simpleHandleException.handle(e, model);
				e.printStackTrace();
			}
		} else {
			model.addAttribute("result","更新失败");
		}
		return "editUser";
	}
	
	@RequestMapping(value = "/service/uploadPhoto",method = RequestMethod.POST)
	public Serializable uploadUserPhoto(MultipartFile file,ModelMap model,HttpSession session){
		UserInfo cur = (UserInfo) session.getAttribute(GlobalValues.SESSION_USER);
		Serializable ret = FileUtil.saveFile(session, file, FileUtil.CATEGORY_PHOTO);
		if ( ret instanceof String){
			String retFilePath = (String) ret;
			cur.setHeadUrl(retFilePath);
			try {
				Serializable retuser = userService.updateUserInfo(cur);
				if ( retuser instanceof UserInfo ){
					session.setAttribute(GlobalValues.SESSION_USER, retuser);
					model.addAttribute("result","更新成功");
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
	/**用户登出调用
	 * @param user 登出前，保存在Session中的user
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/loginOut.do",method = {RequestMethod.POST,RequestMethod.GET})
	public String logOut(
			UserInfo user,
			HttpSession httpSession){
		httpSession.removeAttribute(GlobalValues.SESSION_USER);
		return "login";
	}
}
