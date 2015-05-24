package com.ccc.test.service.impl;

import java.io.Serializable;

import com.ccc.test.pojo.GroupInfo;
import com.ccc.test.service.interfaces.IGroupService;

public class GroupServiceImpl implements IGroupService{

	@Override
	public Serializable create_group(Integer teacherID, String groupName,
			String description) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable deleteGroup(Integer teacherId, Integer groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable updateGroup(Integer teacherId, Integer groupId,
			GroupInfo groupInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable queryGroup(Integer requestId, Integer groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable teaQueryGroups(Integer requestId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable queryStuList(Integer groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable stuQueryGroups(Integer requestId) {
		// TODO Auto-generated method stub
		return null;
	}

}
