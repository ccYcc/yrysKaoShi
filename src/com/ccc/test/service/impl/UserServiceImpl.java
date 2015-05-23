package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.service.interfaces.IUserService;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;
import com.ccc.test.utils.SecurityMethod;

//代表服务层
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	IBaseHibernateDao<UserInfo> userDao ;
	
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

}
