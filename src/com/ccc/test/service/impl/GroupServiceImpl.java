package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccc.test.pojo.GroupInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.PaperGroupRelationInfo;
import com.ccc.test.pojo.TeacherPaperInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserGroupRelationInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.pojo.ValidtionInfo;
import com.ccc.test.service.interfaces.IGroupService;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;
import com.ccc.test.utils.UtilDao;

public class GroupServiceImpl implements IGroupService{

	@Override
	public Serializable create_group(Integer requestId,String groupName,
			String description)  {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		GroupInfo group = new GroupInfo();
		Long createTime = System.currentTimeMillis();
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
//		删除GroupInfo表记录
		try 
		{
			map.put(GroupInfo.COLUMN_ID, groupId);
			if(!UtilDao.Delete(groupInfo, map))
			{
				msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_ENTITY);
				return msg;
			}
	//		删除UserGroupRelationInfo表记录
			map.clear();
			map.put(UserGroupRelationInfo.COLUMN_GROUPID, groupId);
			UtilDao.Delete(userGroup, map);
		}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_DELETE_FAILED, GlobalValues.MSG_DELETE_FAILED);
			return msg;
		}
		msg.setMsg(GlobalValues.CODE_DELETE_SUCCESS, GlobalValues.MSG_DELETE_SUCCESS);
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
		Long createTime = System.currentTimeMillis();
		groupInfo.setCreateTime(createTime);
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
			msg.setMsg(GlobalValues.CODE_UPDATE_SUCCESS, GlobalValues.MSG_UPDATE_SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_UPDATE_FAILED, GlobalValues.MSG_UPDATE_FAILED);
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
		try {
		if(userType==0)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(GroupInfo.COLUMN_OWNERID,requestId);
			groupInfos = UtilDao.getList(groupInfo, map);
			if(groupInfos==null)
			{
				msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_ARGS);
				return msg;
			}
			if(groupInfos.size()==0)
			{
				msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_LIST);
				return msg;
			}
			return (Serializable) groupInfos;
		}
		else if(userType==1) {
			groupInfos = new ArrayList<GroupInfo>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(UserGroupRelationInfo.COLUMN_USERID,requestId);
			userGroups = UtilDao.getList(userGroup, map);
			if(userGroups==null)
			{
				msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_ARGS);
				return msg;
			}
			if(userGroups.size()==0)
			{
				msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_LIST);
				return msg;
			}
			for(UserGroupRelationInfo info:userGroups)
			{
				GroupInfo group_info;	
				group_info = UtilDao.getById(groupInfo, info.getGroupId());
				groupInfos.add(group_info);
			}
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_FETCH_FAILED, GlobalValues.MSG_FETCH_FAILED);
			return msg;
		}
		return (Serializable) groupInfos;
	}
	@Override
	public Serializable queryStuList(Integer groupId) {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		UserGroupRelationInfo userGroup = new UserGroupRelationInfo();
		UserInfo user = new UserInfo();
		List<UserGroupRelationInfo> user_Groups;
		List<UserInfo> users = new ArrayList<UserInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserGroupRelationInfo.COLUMN_GROUPID,groupId);
		try {
			user_Groups = UtilDao.getList(userGroup, map);
			if(user_Groups==null)
			{
				msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_ARGS);
				return msg;
			}
			if(user_Groups.size()==0)
			{
				msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_LIST);
				return msg;
			}
			for(UserGroupRelationInfo info:user_Groups)
			{
				UserInfo userInfo;
				userInfo = UtilDao.getById(user, info.getUserId()); 
				users.add(userInfo);
			}
		}catch (Exception e) {
				// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_FETCH_FAILED, GlobalValues.MSG_FETCH_FAILED);
			return msg;
		}
		return (Serializable) users;
	}

	@Override
	public Serializable hasNewInfo(Integer acceptId) {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ValidtionInfo.COLUMN_ACCEPT_ID, acceptId);
		ValidtionInfo valid = new ValidtionInfo();
		List<ValidtionInfo> validtionInfos = null;
		try {
			validtionInfos = UtilDao.getList(valid ,map);
			if(validtionInfos==null)
			{
				msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_ARGS);
				return msg;
			}
			if(validtionInfos.size()==0)
			{
				msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY,GlobalValues.MSG_EMPTY_LIST);
				return msg;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_FETCH_FAILED,GlobalValues.MSG_FETCH_FAILED);
			return msg;
		}
		return (Serializable) validtionInfos;
	}

	@Override
	public Serializable fetchUser(List<ValidtionInfo> validations)
			throws Exception {
		MsgInfo msg = new MsgInfo();
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		UserInfo userInfo = new UserInfo();
		if(validations.size()==0||validations==null)
		{
			msg.setMsg(GlobalValues.CODE_EMPTY_LIST, GlobalValues.MSG_EMPTY_LIST);
			return msg;
		}
		
		for(ValidtionInfo validate:validations)
		{
			UserInfo user = null;
			try {
				user = UtilDao.getById(userInfo, validate.getRequest_id());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				msg.setMsg(GlobalValues.CODE_FETCH_FAILED,GlobalValues.MSG_FETCH_FAILED);
				return msg;
			}
			userInfos.add(user);
		}
		return (Serializable) userInfos;
	}

	@Override
	public Serializable fetchGroup(List<ValidtionInfo> validations) {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		List<GroupInfo> groupInfos = new ArrayList<GroupInfo>();
		GroupInfo groupInfo = new GroupInfo();
		
		if(validations.size()==0)
		{
			msg.setMsg(GlobalValues.CODE_EMPTY_LIST, GlobalValues.MSG_EMPTY_LIST);
			return msg;
		}
		
		for(ValidtionInfo validate:validations)
		{
			GroupInfo group = null;
			try {
				group = UtilDao.getById(groupInfo, validate.getGroupId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				msg.setMsg(GlobalValues.CODE_FETCH_FAILED,GlobalValues.MSG_FETCH_FAILED);
				return msg;
			}
			groupInfos.add(group);
		}
		return (Serializable) groupInfos;
	}

	@Override
	public Serializable fetchPaper(Integer groupId){
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		List<TeacherPaperInfo> papers = new ArrayList<TeacherPaperInfo>();
		TeacherPaperInfo paper = new TeacherPaperInfo();
//		GroupInfo group = new GroupInfo();
		PaperGroupRelationInfo paperGroup = new PaperGroupRelationInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(PaperGroupRelationInfo.COLUMN_GROUPID, groupId);
		List<PaperGroupRelationInfo> paperGroups = null;
//		GroupInfo groupInfo = null;
		try {
			paperGroups = UtilDao.getList(paperGroup, map);
			if(paperGroups==null||paperGroups.size()==0)
			{
				msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_ENTITY);
				return msg;
			}
			for(PaperGroupRelationInfo paperGroupInfo:paperGroups)
			{
				TeacherPaperInfo paperInfo = UtilDao.getById(paper, paperGroupInfo.getPaperId());
				papers.add(paperInfo);
			}
//			groupInfo = UtilDao.getById(group, groupId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_FETCH_FAILED, GlobalValues.MSG_FETCH_FAILED);
			return msg;
		}
//		groupInfo.setPapers(papers);ListUtil
		return (Serializable) papers;
	}

	@Override
	public Serializable fetchQuestions(Integer paperId) {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		TeacherPaperInfo paperInfo = new TeacherPaperInfo();
		List<QuestionInfo> questions = new ArrayList<QuestionInfo>();
		QuestionInfo questInfo = new QuestionInfo();
		List<Integer> questionIds;
		TeacherPaperInfo paper;
		try {
			paper = UtilDao.getById(paperInfo,paperId);
			questionIds = ListUtil.stringsToTListSplitBy(paper.getQuestionIds(), ",");
			if(ListUtil.isEmpty(questionIds))
			{
				msg.setMsg(GlobalValues.CODE_EMPTY_LIST, GlobalValues.MSG_EMPTY_LIST);
				return msg;
			}
			for(Integer questionId:questionIds)
			{
				QuestionInfo quest = UtilDao.getById(questInfo, questionId);
				questions.add(quest);
			}
		} catch (Exception e) {
			msg.setMsg(GlobalValues.CODE_FETCH_FAILED, GlobalValues.MSG_FETCH_FAILED);
			return msg;
		}
		
		return (Serializable) questions;
	}

	@Override
	public Serializable fetchPaperById(Integer paperId) {
		// TODO Auto-generated method stub
		TeacherPaperInfo paperInfo = new TeacherPaperInfo();
		MsgInfo msg = new MsgInfo();
		TeacherPaperInfo paper;
		try {
			paper = UtilDao.getById(paperInfo,paperId);
		} catch (Exception e) {
			msg.setMsg(GlobalValues.CODE_FETCH_FAILED, GlobalValues.MSG_FETCH_FAILED);
			return msg;
		}
		return paper;
	}
}
