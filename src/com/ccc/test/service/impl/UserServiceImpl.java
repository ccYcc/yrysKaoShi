package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.UserGroupRelationInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.pojo.ValidtionInfo;
import com.ccc.test.service.interfaces.IUserService;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;
import com.ccc.test.utils.SecurityMethod;
import com.ccc.test.utils.UtilDao;

//代表服务层
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	IBaseHibernateDao<UserInfo> userDao ;
	public static String defaultHeadUrl = "./img/default_user_pic.jpg";
	@Override
	public String login(String username, String password,String type) throws Exception {
		String md5psw = SecurityMethod.encryptMD5(SecurityMethod.encryptMD5(password));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserInfo.COLUMN_USER_NAME, username);
		map.put(UserInfo.COLUMN_PASSWORD, md5psw);
		map.put(UserInfo.COLUMN_TYPE, type);
		List<UserInfo> users = userDao.getList(map);
		if ( ListUtil.isEmpty(users) ){
			return "-1";
		} else {
			return users.get(0).getId()+"";
		}
	}

	@Override
	public Serializable fetchUserInfo(String token) throws Exception {
		Integer id = Integer.valueOf(token);
		MsgInfo msg = new MsgInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserInfo.COLUMN_ID, id);
		List<UserInfo> users = userDao.getList(map);
		if ( ListUtil.isEmpty(users) ){
			msg.setMsg(GlobalValues.CODE_USER_PSW_NOT_MATCH
					, GlobalValues.MSG_USER_PSW_NOT_MATCH);
			return msg;
		} else {
			return users.get(0);
		}
	}

	@Override
	public Serializable loginOut(String token) throws Exception {
		return false;
	}

	@Override
	public Serializable register(String username, String password,
			String conPassword, String type) throws Exception {
		MsgInfo msg = new MsgInfo();
		if ( ListUtil.isEmpty(username)
				|| ListUtil.isEmpty(password)
				|| ListUtil.isEmpty(conPassword)
				|| ListUtil.isEmpty(type) 
				){
			msg.setMsg(GlobalValues.CODE_EMPTY_INPUT, GlobalValues.MSG_EMPTY_INPUT);
		} else if( !password.equals(conPassword) ){
			msg.setMsg(GlobalValues.CODE_PASSWORD_NOT_MATCH
					, GlobalValues.MSG_PASSWORD_NOT_MATCH);
		} else {
			msg.setMsg(GlobalValues.CODE_USERNAME_USED
					, GlobalValues.MSG_USERNAME_USED);
			String md5psw = SecurityMethod.encryptMD5(SecurityMethod.encryptMD5(password));
			UserInfo t = new UserInfo();
			t.setCreateTime(System.currentTimeMillis());
			t.setPassword(md5psw);
			t.setUsername(username);
			t.setType(type);
			t.setHeadUrl(defaultHeadUrl);
			Serializable ret = userDao.add(t);
			Integer uid = (Integer) ret;
			if( uid == -1 ){
				msg.setMsg(GlobalValues.CODE_USERNAME_USED
						, GlobalValues.MSG_USERNAME_USED);
			} else if ( uid > 0 ){
				msg.setMsg(GlobalValues.CODE_SUCCESS
						, GlobalValues.MSG_REG_SUCCESS);
			}
		}
		return msg;
	}

	@Override
	public Serializable updateUserInfo(UserInfo info) throws Exception {
		boolean ret = userDao.update(info);
		MsgInfo  msg = new MsgInfo();
		if ( !ret ){
			msg.setMsg(GlobalValues.CODE_UPDATE_INFO_ERROR
					, GlobalValues.MSG_UPDATE_INFO_ERROR);
			return msg;
		} else {
			return fetchUserInfo(""+info.getId());
		}
	}

	@Override
	public Serializable joinGroup(Integer requestId, Integer acceptId,
			Integer groupId, String message,long createTime) throws Exception {
		// TODO Auto-generated method stub
		ValidtionInfo valInfo = new ValidtionInfo();
		MsgInfo  msg = new MsgInfo();
//		查询该学生是否已经在该班级中
		UserGroupRelationInfo userGroup = new UserGroupRelationInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserGroupRelationInfo.COLUMN_USERID,requestId);
		map.put(UserGroupRelationInfo.COLUMN_GROUPID, groupId);
		List<UserGroupRelationInfo> userGroups = UtilDao.getList(userGroup, map); 
		if(userGroups.size()>0)
		{
			msg.setMsg(GlobalValues.CODE_JOIN_FAILED, GlobalValues.MSG_ALREADY_IN);
			return msg;
		}
		else {
//			在验证数据表中添加记录
			valInfo.setAccept_id(acceptId);
			valInfo.setRequest_id(requestId);
			valInfo.setAccept_id(acceptId);
			valInfo.setCreateTime(createTime);
			valInfo.setMessage(message);
			int flag = (Integer) UtilDao.add(valInfo);
			if(flag==-1)
				msg.setMsg(GlobalValues.CODE_ADD_FAILED,GlobalValues.MSG_ADD_FAILED);
			else {
				msg.setMsg(GlobalValues.CODE_SUCCESS, GlobalValues.MSG_SUCCESS);
			}
		}
		return msg;
	}

	@Override
	public Serializable deleteValidate(Integer requestId, Integer groupId) {
		// TODO Auto-generated method stub
		MsgInfo  msg = new MsgInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		ValidtionInfo valInfo = new ValidtionInfo();
		map.put(ValidtionInfo.COLUMN_REQUEST_ID,requestId);
		map.put(ValidtionInfo.COLUMN_GROUPID, groupId);
		try {
			UtilDao.Delete(valInfo, map);
			msg.setMsg(GlobalValues.CODE_SUCCESS, GlobalValues.MSG_SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_FAILED, GlobalValues.MSG_FAILED);
			return msg;
		}
		return msg;
	}

	@Override
	public Serializable quitGroup(Integer requestId, Integer groupId) {
		// TODO Auto-generated method stub
		MsgInfo  msg = new MsgInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		UserGroupRelationInfo user_group = new UserGroupRelationInfo();
		map.put(UserGroupRelationInfo.COLUMN_USERID,requestId);
		map.put(ValidtionInfo.COLUMN_GROUPID, groupId);
		try {
			UtilDao.Delete(user_group, map);
			msg.setMsg(GlobalValues.CODE_SUCCESS, GlobalValues.MSG_SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_FAILED, GlobalValues.MSG_FAILED);
			return msg;
		}
		
		return msg;
	}

}
