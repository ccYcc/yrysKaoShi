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
import com.sun.org.apache.bcel.internal.generic.RETURN;

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
	public Serializable handleRequest(Integer groupId,
									  Integer requsestId,
									  Integer acceptId,
									  String message,
									  Integer handleType,
									  long createTime)  {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		ValidtionInfo valInfo = new ValidtionInfo();
//		同意加入
		if(handleType==1)
		{
//			写入学生——班级关系表
			UserGroupRelationInfo user_group = new UserGroupRelationInfo();
			user_group.setGroupId(groupId);
			user_group.setUserId(requsestId);
			user_group.setCreateTime(createTime);
			try {
				UtilDao.add(user_group);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				msg.setMsg(GlobalValues.CODE_ADD_FAILED, GlobalValues.MSG_ADD_FAILED);
				return msg;
			}
		}
//		先删除以前的记录
		deleteValidate(requsestId,groupId);
//			在验证数据表中添加记录
		valInfo.setRequest_id(acceptId);
		valInfo.setAccept_id(requsestId);
		valInfo.setCreateTime(createTime);
		valInfo.setMessage(message);
		try {
			UtilDao.add(valInfo);
			msg.setMsg(GlobalValues.CODE_SUCCESS, GlobalValues.MSG_SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_ADD_FAILED,GlobalValues.MSG_ADD_FAILED);
			return msg;
		}
		return msg;
	}

	@Override
	public Serializable deleteValidate(Integer requestId, Integer groupId)
			 {
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
	
}
