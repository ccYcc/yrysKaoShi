package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.support.DaoSupport;

import com.ccc.test.pojo.DiyPaperInfo;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.KnowledgeQuestionRelationInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;
import com.ccc.test.service.interfaces.IDiyPaperService;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;
import com.ccc.test.utils.UtilDao;

public class DiyPaperServiceImpl implements IDiyPaperService {

	@Override
	public Serializable writeAnsLogs(List<UserAnswerLogInfo> answerLogs) {
		// TODO Auto-generated method stub
//		List<Integer> logList = new ArrayList<Integer>();
		MsgInfo msg = new MsgInfo();
		if(ListUtil.isEmpty(answerLogs))
		{
			msg.setMsg(GlobalValues.CODE_EMPTY_LIST, GlobalValues.MSG_EMPTY_LIST);
			return msg;
		}
		try {
			for(UserAnswerLogInfo ansLog:answerLogs)
			{
				
					Integer id = (Integer) UtilDao.add(ansLog);
					ansLog.setId(id);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_ADD_FAILED, GlobalValues.MSG_ADD_FAILED);
			return msg;
		}
		msg.setMsg(GlobalValues.CODE_SUCCESS, GlobalValues.MSG_UPDATE_SUCCESS);
		return (Serializable) msg;
	}
	@Override
	public Serializable updateQuestion(List<UserAnswerLogInfo> answerLogs) {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		QuestionInfo questionInfo = new QuestionInfo();
		if(ListUtil.isEmpty(answerLogs))
		{
			msg.setMsg(GlobalValues.CODE_EMPTY_LIST, GlobalValues.MSG_EMPTY_LIST);
			return msg;
		}
		try 
		{
			for(UserAnswerLogInfo ansLog:answerLogs)
			{
//				获取题目信息
				QuestionInfo quest = UtilDao.getById(questionInfo, ansLog.getQid());
//				更新题目信息
				int rightCount = quest.getRightCount();
				int wrongCount = quest.getWrongCount();
				float avgTime = quest.getAvgTime();
				float totalTime = (rightCount+wrongCount)*avgTime;
				int ans_result = ansLog.getAnsResult();
				if(ans_result!=0)
					wrongCount+=1;
				else
					rightCount+=1;
				float useTime = ansLog.getUsedTime();
				totalTime+=useTime;
				avgTime = totalTime/(rightCount+wrongCount);
				quest.setAvgTime(avgTime);
				quest.setWrongCount(wrongCount);
				quest.setRightCount(rightCount);
				UtilDao.update(quest);
			}
		} catch (Exception e) {
			// TODO: handle exception
			msg.setMsg(GlobalValues.CODE_UPDATE_FAILED, GlobalValues.MSG_UPDATE_FAILED);
			return msg;
		}
		msg.setMsg(GlobalValues.CODE_SUCCESS, GlobalValues.MSG_UPDATE_SUCCESS);
		return msg;
	}

	@Override
	public Serializable createPaper(String knowledges, 
									String paperName,
									List<UserAnswerLogInfo> answerLogs,
									Integer userId,
									String paperLevel,
									String learnLevel,
									String goodKnowledges,
									String badKnowledges,
									String midKnowledges,
									String recommendsQuestions) {
		// TODO Auto-generated method stub
		
		MsgInfo msg = new MsgInfo();
		DiyPaperInfo diyPaper = new DiyPaperInfo();
		long createTime = System.currentTimeMillis();
		diyPaper.setUid(userId);
		diyPaper.setChooseKnowledges(knowledges);
		diyPaper.setGoodKnowledges(goodKnowledges);
		diyPaper.setLearnLevel(learnLevel);
		diyPaper.setPaperLevel(paperLevel);
		diyPaper.setRecommendQuestions(recommendsQuestions);
		diyPaper.setMidKnowledges(midKnowledges);
		diyPaper.setPaperName(paperName);
		diyPaper.setCreateTime(createTime);
		int rightCounts = 0;
		int wrongCounts = 0;
		Long useTime = (long) 0;
		List<Integer> idList = new ArrayList<Integer>();
		for(UserAnswerLogInfo ansLog:answerLogs)
		{
			idList.add(ansLog.getId());
			if(ansLog.getAnsResult()==0)
				rightCounts+=1;
			else
				wrongCounts+=1;
			useTime+=ansLog.getUsedTime();
		}
		String idString = ListUtil.listToStringJoinBySplit(idList, ",");
		diyPaper.setRightCounts(rightCounts);
		diyPaper.setWrongCounts(wrongCounts);
		diyPaper.setAnswer_logs(idString);
		diyPaper.setUseTime(useTime);
		int diyPid = 0;
		try {
		    diyPid = (Integer) UtilDao.add(diyPaper);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_CREATE_FAILED, GlobalValues.MSG_CREATE_FAILED);
		}
		return diyPid;
	}

	@Override
	public Serializable fetchUserPaperList(Integer userId) {
		// TODO Auto-generated method stub
		Map<String,Object> args =new HashMap<String,Object>();
		args.put(DiyPaperInfo.COLUMN_UID, userId);
		try {
			return (Serializable) UtilDao.getList(new DiyPaperInfo(), args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Serializable fetchUserPaper(Integer paperId) {
		// TODO Auto-generated method stub
		DiyPaperInfo diyPaper;
		try {
			diyPaper = UtilDao.getById(new DiyPaperInfo(), paperId);
			if(diyPaper==null)return null;
			//set AnswerLogList
			List<UserAnswerLogInfo> loginfo_list = (List<UserAnswerLogInfo>) useIDStringToList(new UserAnswerLogInfo(),diyPaper.getAnswer_logs(), ",");
			diyPaper.setAnswerLogInfos(loginfo_list);
			if(loginfo_list==null)return null;
			//set QuestionList
			List<String>Qid_list=new ArrayList<String>();
			for(UserAnswerLogInfo loginfo : loginfo_list)
				Qid_list.add(""+loginfo.getQid());
			List<QuestionInfo>question_list =  (List<QuestionInfo>) useIDStringToList(new QuestionInfo(),ListUtil.listToStringJoinBySplit(Qid_list, ","), ",");
			//填充QuestionInfo中德知识点list
			Map<Integer,KnowledgeInfo>kinfoMap = new HashMap<Integer, KnowledgeInfo>();
			if(question_list!=null)
			for(QuestionInfo qinfo : question_list)
			{
				Map<String,Object>argsmap = new HashMap<String, Object>();
				argsmap.put(KnowledgeQuestionRelationInfo.COLUMN_QuestionID, qinfo.getId());
				List<KnowledgeQuestionRelationInfo> kqList = UtilDao.getList(new KnowledgeQuestionRelationInfo(), argsmap);
				List<KnowledgeInfo> kList = new ArrayList<KnowledgeInfo>();
				if(kqList!=null)
				{
					for(KnowledgeQuestionRelationInfo kqinfo : kqList)
					{
						KnowledgeInfo kinfo = kinfoMap.get(kqinfo.getKnoeledgeId());
						if(kinfo==null)
						{
							kinfo = UtilDao.getById(new KnowledgeInfo(), kqinfo.getKnoeledgeId());
							kinfoMap.put(kqinfo.getKnoeledgeId(), kinfo);
						}
						if(kinfo!=null)
							kList.add(kinfo);
					}
				}
				qinfo.setKnowledges(kList);
			}
			diyPaper.setQuestionInfos(question_list);
			//set ChooseKnowledgeList
			List<KnowledgeInfo> Knowledgeinfo_list = (List<KnowledgeInfo>) useIDStringToList(new KnowledgeInfo(),diyPaper.getChooseKnowledges(), ",");
			diyPaper.setChooseKnowledgeInfos(Knowledgeinfo_list);
			//set goodorbadKnowledgeList
			Map<String,String>goodorbadKnowledgeMap = new HashMap<String, String>();
			List<String>goodList=ListUtil.stringsToListSplitBy(diyPaper.getGoodKnowledges(), ",");
			if(goodList!=null)
				for(String temp : goodList)
				{
					goodorbadKnowledgeMap.put(temp, "good");
				}
			List<String>badList=ListUtil.stringsToListSplitBy(diyPaper.getBadKnowledges(), ",");
			if(badList!=null)
				for(String temp : badList)
				{
					goodorbadKnowledgeMap.put(temp, "bad");
				}
			List<KnowledgeInfo> goodKnowledge = new ArrayList<KnowledgeInfo>();
			List<KnowledgeInfo> badKnowledge = new ArrayList<KnowledgeInfo>();		
			//goodlst 与 badlist 包含于ChooseKnowledgeList，所以直接在ChooseKnowledgeList中取出
			if(Knowledgeinfo_list!=null)
				for(KnowledgeInfo temp : Knowledgeinfo_list) 
				{
					String values = goodorbadKnowledgeMap.get(temp.getId()+"");
					if(values==null)continue;
					if(values.equals("good"))
						goodKnowledge.add(temp);
					else
						badKnowledge.add(temp);
				}
			diyPaper.setGoodKnowledgeInfos(goodKnowledge);
			diyPaper.setBadKnowledgeInfos(badKnowledge);
			return diyPaper;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param t
	 * @param ids id串，逗号分隔
	 * @param split
	 * @return 返回t的list
	 */
	public <T> Serializable useIDStringToList(T t , String ids , String split)
	{
		try {
			List<T> info_list=new ArrayList<T>();
			List<String> ID_list = ListUtil.stringsToListSplitBy(ids, split);
			if(ID_list==null||ID_list.size()<=0)return null;
			for(String Id : ID_list)
			{
				if(StringUtils.isBlank(Id))continue;
				if(!StringUtils.isNumeric(Id)&&!(
						Id.substring(0, 1).equals("-")
						&&StringUtils.isNumeric(Id.substring(1, Id.length()))
						))continue;
				Integer logid = Integer.parseInt(Id);
				T info = UtilDao.getById(t, logid);
				if(info!=null)
					info_list.add(info);
			}
			return (Serializable) info_list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
