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
									String question_ids,
									Integer teacher_id) {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		PaperInfo paperInfo = new PaperInfo();
		paperInfo.setPaperUrl(paper_url);
		paperInfo.setName(paperName);
		Long create_time = System.currentTimeMillis();
	    paperInfo.setCreateTime(create_time);
		paperInfo.setQuestionIds(question_ids);
		paperInfo.setTeacher_id(teacher_id);
		try {
			UtilDao.add(paperInfo);
			msg.setMsg(GlobalValues.CODE_UPLOAD_SUCCESS
					, GlobalValues.MSG_UPLOAD_SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_UPLOAD_FAILED
					, GlobalValues.MSG_FAILED);
			return msg;
		}
		return msg;
	}

	@Override
	public Serializable create_group(Integer teacherID, 
									 String groupName,
									 String description){
		// TODO Auto-generated method stub 
		MsgInfo msg = new MsgInfo();
		GroupInfo group = new GroupInfo();
		group.setOwnerId(teacherID);
		group.setName(groupName);
		group.setDescription(description);
		Long createTime = System.currentTimeMillis();
		group.setCreateTime(createTime);
		int groupID;
		try {
			groupID = (Integer) UtilDao.add(group);
			msg.setMsg(GlobalValues.CODE_CREATE_SUCCESS
					, GlobalValues.MSG_CREATE_SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_CREATE_FAILED
					, GlobalValues.MSG_CREATE_FAILED);
		}
		return msg;
	}
	@Override
	public Serializable handleRequest(Integer groupId,
									  Integer requsestId,
									  Integer acceptId,
									  String message,
									  Integer handleType)  {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		ValidtionInfo valInfo = new ValidtionInfo();
		Long createTime = System.currentTimeMillis();
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
		msg = (MsgInfo) deleteValidate(requsestId,groupId);
//		在验证数据表中添加记录
		valInfo.setRequest_id(acceptId);
		valInfo.setAccept_id(requsestId);
		valInfo.setCreateTime(createTime);
		valInfo.setGroupId(groupId);
		valInfo.setMessage(message);
		try {
			UtilDao.add(valInfo);
			msg.setMsg(GlobalValues.CODE_ADD_SUCCESS, GlobalValues.MSG_ADD_SUCCESS);
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
			msg.setMsg(GlobalValues.CODE_DELETE_SUCCESS, GlobalValues.MSG_DELETE_SUCCESS);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_DELETE_FAILED, GlobalValues.MSG_DELETE_FAILED);
			return msg;
		}
		return msg;
	}
	
}
