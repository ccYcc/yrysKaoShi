package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccc.test.pojo.GroupInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.PaperInfo;
import com.ccc.test.pojo.UserGroupRelationInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.pojo.ValidtionInfo;
import com.ccc.test.service.interfaces.ITeacherService;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.UtilDao;

public class TeacherServiceImpl implements ITeacherService{

	@Override
	public Serializable uploadPaper(String paper_url,
									String paperName,
									Long create_time,
									String question_ids,
									Integer teacher_id) throws Exception {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		PaperInfo paperInfo = new PaperInfo();
		paperInfo.setPaperUrl(paper_url);
		paperInfo.setName(paperName);
	    paperInfo.setCreateTime(create_time);
		paperInfo.setQuestionIds(question_ids);
		paperInfo.setTeacher_id(teacher_id);
		Serializable ret = UtilDao.add(paperInfo);
		Integer uid = (Integer) ret;
		if( uid == -1 ){
			msg.setMsg(GlobalValues.CODE_ADD_FAILED
					, GlobalValues.MSG_FAILED);
		} else if ( uid > 0 ){
			msg.setMsg(GlobalValues.CODE_SUCCESS
					, GlobalValues.MSG_SUCCESS);
		}
		return msg;
	}

	@Override
	public Serializable create_group(Integer teacherID, String groupName,
			String description) throws Exception {
		// TODO Auto-generated method stub 
		MsgInfo msg = new MsgInfo();
		long createTime = System.currentTimeMillis();
		GroupInfo group = new GroupInfo();
		group.setOwnerId(teacherID);
		group.setName(groupName);
		group.setDescription(description);
		group.setCreateTime(createTime);
		int groupID = (Integer) UtilDao.add(group);
		if( groupID == -1 ){
			msg.setMsg(GlobalValues.CODE_ADD_FAILED
					, GlobalValues.MSG_FAILED);
		} else if ( groupID > 0 ){
			msg.setMsg(GlobalValues.CODE_SUCCESS
					, GlobalValues.MSG_SUCCESS);
		}
		return msg;
	}
	@Override
	public Serializable hasJoinRequest(Integer teacher_id) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ValidtionInfo.COLUMN_ACCEPT_ID, teacher_id);
		ValidtionInfo valid = new ValidtionInfo();
		List<ValidtionInfo> validtionInfos = UtilDao.getList(valid ,map);
		return (Serializable) validtionInfos;
	}

	@Override
	public  Serializable fetchInfor(List<ValidtionInfo> validations) throws Exception {
		// TODO Auto-generated method stub
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		UserInfo userInfo = new UserInfo();
		for(ValidtionInfo validate:validations)
		{
			UserInfo user = UtilDao.getById(userInfo, validate.getRequest_id());
			userInfos.add(user);
		}
		return (Serializable) userInfos;
	}
	@Override
	public Serializable handleRequest(Integer group_id,List<Integer> uids,long create_time) throws Exception {
		// TODO Auto-generated method stub
//		MsgInfo msg = new MsgInfo();
//		保存返回的结果
		List<Integer> ids = new ArrayList<Integer>();
		for(Integer uid:uids)
		{
			UserGroupRelationInfo user_group = new UserGroupRelationInfo();
			user_group.setGroupId(group_id);
			user_group.setUserId(uid);
			user_group.setCreateTime(create_time);
			int flag = (Integer) UtilDao.add(user_group);
			ids.add(flag);
		}
		return (Serializable) ids;
	}
	
}
