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
import com.ccc.test.pojo.UserGroupRelationInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.pojo.ValidtionInfo;
import com.ccc.test.service.interfaces.ITeacherService;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;
import com.ccc.test.utils.StringUtil;
import com.ccc.test.utils.UtilDao;

public class TeacherServiceImpl implements ITeacherService{

	@Override
	public Serializable uploadPaper(String paper_url,
									String paperName,
									String question_ids,
									Integer teacher_id,
									String gids) 
	{
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		List<Integer> gidList = null;
//		如果没有选择试卷对应的班级，则返回错误
		if(StringUtil.isEmpty(gids))
		{
			msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_ENTITY);
			return msg;
		}
		gidList = ListUtil.stringsToTListSplitBy(gids, ",");
		if(gidList.isEmpty())
		{
			msg.setMsg(GlobalValues.CODE_EMPTY_LIST, GlobalValues.MSG_EMPTY_LIST);
			return msg;
		}
		int paperId = 0;
		try {
			TeacherPaperInfo paperInfo = new TeacherPaperInfo();
			paperInfo.setPaperUrl(paper_url);
			paperInfo.setName(paperName);
			Long create_time = System.currentTimeMillis();
		    paperInfo.setCreateTime(create_time);
			paperInfo.setQuestionIds(question_ids);
			paperInfo.setTeacher_id(teacher_id);
			paperId = (Integer) UtilDao.add(paperInfo);
//			写入试卷_班级对应关系
			List<PaperGroupRelationInfo> paperGroups = new ArrayList<PaperGroupRelationInfo>();
			for(Integer gid:gidList)
			{
				PaperGroupRelationInfo paperGroup = new PaperGroupRelationInfo();
				paperGroup.setCreateTime(create_time);
				paperGroup.setGroupId(gid);
				paperGroup.setPaperId(paperId);
				paperGroups.add(paperGroup);
			}
			if(UtilDao.addAll(paperGroups)==null)
			{
				msg.setMsg(GlobalValues.CODE_UPLOAD_FAILED
						, GlobalValues.MSG_FAILED);
				return msg;
			}
		}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_UPLOAD_FAILED
					, GlobalValues.MSG_FAILED);
			return msg;
		}
		return paperId;
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
		int groupID = 0;
		try {
			groupID = (Integer) UtilDao.add(group);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_CREATE_FAILED
					, GlobalValues.MSG_CREATE_FAILED);
		}
		return groupID;
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
		try {
//		同意加入
		if(handleType==1)
		{
//			写入学生——班级关系表
			UserGroupRelationInfo user_group = new UserGroupRelationInfo();
			user_group.setGroupId(groupId);
			user_group.setUserId(requsestId);
			user_group.setCreateTime(createTime);
			UtilDao.add(user_group);
		}
//		先删除以前的记录
		msg = (MsgInfo) deleteValidate(requsestId,groupId);
//		在验证数据表中添加记录
		valInfo.setRequest_id(acceptId);
		valInfo.setAccept_id(requsestId);
		valInfo.setCreateTime(createTime);
		valInfo.setGroupId(groupId);
		valInfo.setMessage(message);
		UtilDao.add(valInfo);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_ADD_FAILED, GlobalValues.MSG_ADD_FAILED);
			return msg;
		}
		msg.setMsg(GlobalValues.CODE_ADD_SUCCESS, GlobalValues.MSG_ADD_SUCCESS);
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
			if(!UtilDao.Delete(valInfo, map))
			{
				msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_ENTITY);
				return msg;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_DELETE_FAILED, GlobalValues.MSG_DELETE_FAILED);
			return msg;
		}
		msg.setMsg(GlobalValues.CODE_DELETE_SUCCESS, GlobalValues.MSG_DELETE_SUCCESS);
		return msg;
	}
	/**
	 * 先判断paper_班级对应记录是否只剩下最后一个，
	 * 如果是则删除paperInfor，如果不是则仅删除paperGroup关系表记录
	 */
	@Override
	public Serializable deletePaper(Integer paperId, Integer groupId) {
		// TODO Auto-generated method stub
		MsgInfo  msg = new MsgInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		TeacherPaperInfo paperInfo = new TeacherPaperInfo();
		PaperGroupRelationInfo paperGroup = new PaperGroupRelationInfo();
		map.put(PaperGroupRelationInfo.COLUMN_PAPERID, paperId);
		List<PaperGroupRelationInfo> paperGroups = null;
		try {
			paperGroups = UtilDao.getList(paperGroup, map);
			if(paperGroups==null)
			{
				msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_ENTITY);
				return msg;
			}
			map.put(PaperGroupRelationInfo.COLUMN_GROUPID, groupId);
			if(!UtilDao.Delete(paperGroup, map))
			{
				msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_ENTITY);
				return msg;
			}
//			删除关系前，记录小于2，则删除paperInfo
			if(paperGroups.size()<2)
			{
				map.clear();
				map.put(TeacherPaperInfo.COLUMN_ID, paperId);
				if(!UtilDao.Delete(paperInfo, map))
				{
					msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_ENTITY);
					return msg;
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_DELETE_FAILED, GlobalValues.MSG_DELETE_FAILED);
			return msg;
		}
		msg.setMsg(GlobalValues.CODE_DELETE_SUCCESS, GlobalValues.MSG_DELETE_SUCCESS);
		return msg;
	}
	
}
