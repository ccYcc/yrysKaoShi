package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.ccc.test.pojo.GroupInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.UserGroupRelationInfo;
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
	public Serializable updateGroup(Integer teacherId, Integer groupId,
			GroupInfo groupInfo) {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		if (groupInfo == null )
		{
			msg.setMsg(GlobalValues.CODE_UPDATE_INFO_ERROR, GlobalValues.MSG_UPDATE_FAILED);
			return msg;
		}
		GroupInfo info;
		try {
			info = UtilDao.getById(groupInfo,groupInfo.getId());
			info.setGroupInfo(groupInfo);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_UPDATE_INFO_ERROR, GlobalValues.MSG_UPDATE_FAILED);
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
			msg.setMsg(GlobalValues.CODE__FETCH_FAILED, GlobalValues.MSG_FETCH_FAILED);
			return msg;
		}
		return groupResult;
	}

	@Override
	public Serializable teaQueryGroups(Integer requestId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable stuQueryGroups(Integer requestId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable queryStuList(Integer groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
