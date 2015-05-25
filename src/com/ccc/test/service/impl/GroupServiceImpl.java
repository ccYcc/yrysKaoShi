package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccc.test.pojo.GroupInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.UserGroupRelationInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.service.interfaces.IGroupService;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.UtilDao;

public class GroupServiceImpl implements IGroupService{

	@Override
	public Serializable create_group(Integer requestId,String groupName,
			Long createTime,String description)  {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		GroupInfo group = new GroupInfo();
		group.setOwnerId(requestId);
		group.setName(groupName);
		group.setDescription(description);
		group.setCreateTime(createTime);
		int groupID;
		try {
			groupID = (Integer) UtilDao.add(group);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_ADD_FAILED
					, GlobalValues.MSG_ADD_FAILED);
			return msg;
		}
		return groupID;
	}

	@Override
	public Serializable deleteGroup(Integer groupId) {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		GroupInfo groupInfo = new GroupInfo();
		UserGroupRelationInfo userGroup = new UserGroupRelationInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(GroupInfo.COLUMN_ID, groupId);
//		删除GroupInfo表记录
		try {
			UtilDao.Delete(groupInfo, map);
			msg.setMsg(GlobalValues.CODE_SUCCESS, GlobalValues.MSG_SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_FAILED, GlobalValues.MSG_DELETE_FAILED);
			return msg;
		}
//		删除UserGroupRelationInfo表记录
		map.clear();
		map.put(UserGroupRelationInfo.COLUMN_GROUPID, groupId);
		try {
			UtilDao.Delete(userGroup, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_FAILED, GlobalValues.MSG_DELETE_FAILED);
			return msg;
		}
		return msg;
	}

	@Override
	public Serializable updateGroup(GroupInfo groupInfo) {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		if (groupInfo == null )
		{
			msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_ENTITY);
			return msg;
		}
		GroupInfo info;
		try {
			info = UtilDao.getById(groupInfo,groupInfo.getId());
			info.setGroupInfo(groupInfo);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_FETCH_FAILED, GlobalValues.MSG_FETCH_FAILED);
			return msg;
		}
		
		try {
			UtilDao.update(info);
			msg.setMsg(GlobalValues.CODE_SUCCESS, GlobalValues.MSG_UPDATE_SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_UPDATE_INFO_ERROR, GlobalValues.MSG_UPDATE_FAILED);
			return msg;
		}
		return msg;
	}

	@Override
	public Serializable queryGroup(Integer groupId) {
		// TODO Auto-generated method stub
		GroupInfo groupInfo = new GroupInfo();
		MsgInfo msg = new MsgInfo();
		GroupInfo groupResult;
		try {
			groupResult = UtilDao.getById(groupInfo,groupId);
		} catch (Exception e) {
			msg.setMsg(GlobalValues.CODE_FETCH_FAILED, GlobalValues.MSG_FETCH_FAILED);
			return msg;
		}
		return groupResult;
	}
	
	@Override
	public Serializable QueryGroups(Integer requestId, Integer userType) {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		GroupInfo groupInfo = new GroupInfo();
		UserGroupRelationInfo userGroup = new UserGroupRelationInfo();
		List<GroupInfo> groupInfos = null;
		List<UserGroupRelationInfo> userGroups = null;
		if(userType==0)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(GroupInfo.COLUMN_OWNERID,requestId);
			try {
				groupInfos = UtilDao.getList(groupInfo, map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				msg.setMsg(GlobalValues.CODE_FETCH_FAILED, GlobalValues.MSG_FETCH_FAILED);
				return msg;
			}
			return (Serializable) groupInfos;
		}
		else if(userType==1) {
			groupInfos = new ArrayList<GroupInfo>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(UserGroupRelationInfo.COLUMN_USERID,requestId);
			try {
				userGroups = UtilDao.getList(userGroup, map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				msg.setMsg(GlobalValues.CODE_FETCH_FAILED, GlobalValues.MSG_FETCH_FAILED);
				return msg;
			}
			for(UserGroupRelationInfo info:userGroups)
			{
				GroupInfo group_info;
				try {
					group_info = UtilDao.getById(groupInfo, info.getGroupId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					msg.setMsg(GlobalValues.CODE_FETCH_FAILED, GlobalValues.MSG_FETCH_FAILED);
					return msg;
				}
				groupInfos.add(group_info);
			}
		}
		return (Serializable) groupInfos;
	}
	@Override
	public Serializable queryStuList(Integer groupId) {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		UserGroupRelationInfo userGroup = new UserGroupRelationInfo();
		UserInfo user = new UserInfo();
		List<UserGroupRelationInfo> userGroups;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserGroupRelationInfo.COLUMN_GROUPID,groupId);
		try {
			userGroups = UtilDao.getList(userGroup, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_FETCH_FAILED, GlobalValues.MSG_FETCH_FAILED);
			return msg;
		}
		List<UserInfo> users = new ArrayList<UserInfo>();
		for(UserGroupRelationInfo info:userGroups)
		{
			UserInfo userInfo = null;
			try {
				userInfo = UtilDao.getById(user, info.getUserId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				msg.setMsg(GlobalValues.CODE_FETCH_FAILED, GlobalValues.MSG_FETCH_FAILED);
				return msg;
			}
			users.add(user);
		}
		return (Serializable) users;
	}
}
