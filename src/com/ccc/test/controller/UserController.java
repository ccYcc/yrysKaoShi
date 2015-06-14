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
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.service.interfaces.IGroupService;
import com.ccc.test.service.interfaces.ITeacherService;
import com.ccc.test.service.interfaces.IUserService;
import com.ccc.test.utils.FileUtil;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;

//代表控制层
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	IUserService userService;
	@Autowired
	ITeacherService teacherService;
	@Autowired
	IGroupService groupService;
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
			HttpSession httpSession,
			RedirectAttributes raModel){
		
			try {
				String tokenid = userService.login(username, password,usertype);
				Serializable ret = userService.fetchUserInfo(tokenid);
				if ( ret instanceof UserInfo ){
					UserInfo user = (UserInfo) ret;
					//addAttribute 值不能为空
					String type = user.getType();
					httpSession.setAttribute(GlobalValues.SESSION_USER,user);
					if ( "学生".equals(type) ){
						return "redirect:/jsp/toStudentMain";
					} else if ( "老师".equals(type) ){
						return "redirect:/jsp/toTeacherMain";
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
	 
	/**搜索接口 目前只搜索老师
	 * @param searchText
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/service/search",method = {RequestMethod.POST,RequestMethod.GET})
	public Serializable search(String searchText,ModelMap model){
		
		Serializable sret = userService.seachUser(searchText, searchText);
		List<UserInfo> users = new ArrayList<UserInfo>();
		try{
			if ( sret instanceof List ){
				users = (List<UserInfo>) sret;
				for ( UserInfo user : users ){
					Serializable gret = groupService.QueryGroups(user.getId(), 0);
					if (gret instanceof List){
						user.setClasses((List<GroupInfo>)gret);
					} else {
//						model.addAttribute("result", gret);
					}
				}
			} else {
//				model.addAttribute("result", sret);
			}
		} catch (Exception e) {
		}
		model.addAttribute("users", users);
		model.addAttribute("searchText",searchText);
		return "searchUserResults";
	}
	
	@RequestMapping(value = "/service/update",method = {RequestMethod.POST,RequestMethod.GET})
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
	
	/**加入班级，发送请求后经老师同意加入。
	 * @param clazzs 班级id，用逗号,隔开
	 * @param model 
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/service/joinGroup",method = RequestMethod.POST)
	public Serializable joinGroup(String searchText,String clazzs,String tid ,
			ModelMap model,HttpSession httpSession,RedirectAttributes raModel,
			HttpServletRequest req){
		UserInfo cur = (UserInfo) httpSession.getAttribute(GlobalValues.SESSION_USER);
		int requestId,acceptId;
		long createTime = System.currentTimeMillis();
		String msg = "";
		if( cur != null ){
			try{
				msg = cur.getUsername()+" 申请加入您的班级!";
				requestId = cur.getId();
				acceptId = Integer.valueOf(tid);
				List<Integer> groupIds = ListUtil.stringsToTListSplitBy(clazzs, ",");
				if ( ListUtil.isNotEmpty(groupIds) ){
					int jCount = groupIds.size();
					int successCount = 0;
					MsgInfo errMsg = null;
					for ( Integer groupId : groupIds ){
						MsgInfo ret = (MsgInfo) userService.joinGroup(requestId, acceptId, groupId, msg, createTime);
						if ( GlobalValues.CODE_ADD_SUCCESS == ret.getCode() ){
							successCount++;
						} else {
							errMsg = ret;
						}
					}
					if ( successCount>0){
						if ( jCount == successCount ){
							model.addAttribute("result","请求发送成功，等待老师回复");
						} else {
							model.addAttribute("result","成功发送"+successCount+"个请求，失败"+(jCount-successCount)+"个");
						}
					} else {
						model.addAttribute("result",errMsg);
					}
				}
			}catch (Exception e) {
				simpleHandleException.handle(e, model);
			}
		}
		raModel.addAttribute("searchText",searchText);
		simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
		return "redirect:search";
	}
	@ResponseBody
	@RequestMapping(value = "/json/quitGroup",method = {RequestMethod.POST,RequestMethod.GET})
	public Serializable quitGroup(String gid,
			ModelMap model,HttpSession httpSession,RedirectAttributes raModel,
			HttpServletRequest req){
		UserInfo cur = (UserInfo) httpSession.getAttribute(GlobalValues.SESSION_USER);
		if( cur != null ){
			try{
				int groupId = Integer.valueOf(gid);
				MsgInfo msg = (MsgInfo) userService.quitGroup(cur.getId(), groupId);
				if ( GlobalValues.CODE_DELETE_SUCCESS == msg.getCode() ){
					return true;
				}
			}catch (Exception e) {
			}
			
		}
		return false;
	}
	
	
	@RequestMapping(value = "/service/uploadPhoto",method = RequestMethod.POST)
	public Serializable uploadUserPhoto(
			MultipartFile file,ModelMap model,HttpSession session,RedirectAttributes raModel){
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
		simpleHandleException.wrapModelMapInRedirectMap(raModel, model);
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
		return "redirect:/jsp/login";
	}
}
