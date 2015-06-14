package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.KnowledgeQuestionRelationInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;
import com.ccc.test.service.interfaces.IAlgorithmService;
import com.ccc.test.service.interfaces.IQuestionService;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;
import com.ccc.test.utils.NumberUtil;
import com.ccc.test.utils.PropertiesUtil;
import com.ccc.test.utils.StringUtil;
import com.ccc.test.utils.UtilDao;

public class IAlgorithmServiceImpl implements IAlgorithmService {

	@Autowired
	IBaseHibernateDao<QuestionInfo> questDao;
	
	@Autowired
	IBaseHibernateDao<KnowledgeInfo> knowledgeDao;
	
	@Autowired
	IBaseHibernateDao<KnowledgeQuestionRelationInfo> knowledge_question_Dao;
	
	@Autowired
	IQuestionService questService;
	
	class Pairs{
		Integer right;
		Integer wrong;
		public Pairs(Integer l,Integer r)
		{
			this.right=l;
			this.wrong=r;
		}
		public Integer getRight() {
			return right;
		}
		public void setRight(Integer right) {
			this.right = right;
		}
		public Integer getWrong() {
			return wrong;
		}
		public void setWrong(Integer wrong) {
			this.wrong = wrong;
		}
	}
	
	@Override
	public Serializable CheckUserGoodBadKnowledges(
			List<UserAnswerLogInfo> answerLogs,
			List<Integer> SelectKnoledgesID) {
		
		//if(SelectKnoledgesID==null||SelectKnoledgesID.size()<=0)return null;
		if(answerLogs==null||answerLogs.size()<=0)return null;
		
		Map<Integer,Pairs>map=new HashMap<Integer,Pairs>();//key：知识点id，value：对与错个数
//		for(Integer KInfo : SelectKnoledgesID)
//			map.put(KInfo, new Pairs(0,0));
		
		for(UserAnswerLogInfo UInfo : answerLogs)//check 每道题的知识点
		{
			Map<String,Object>args = new HashMap<String, Object>();
			args.put(KnowledgeQuestionRelationInfo.COLUMN_QuestionID, UInfo.getQid());
			List<KnowledgeQuestionRelationInfo> KQInfo;
			try {
				KQInfo = knowledge_question_Dao.getList(args);
				if(KQInfo==null || KQInfo.size()<=0)continue;
				for(KnowledgeQuestionRelationInfo kqinfo : KQInfo)
				{
					Pairs pairs= map.get(kqinfo.getKnoeledgeId());
//					if(pairs!=null)
//					{
//						pairs.setRight(pairs.getRight()+(UInfo.getAnsResult()==0?1:0));
//						pairs.setWrong(pairs.getWrong()+(UInfo.getAnsResult()==0?0:1));
//					}
					if(pairs==null)pairs = new Pairs(0,0);
					pairs.setRight(pairs.getRight()+(UInfo.getAnsResult()==0?1:0));
					pairs.setWrong(pairs.getWrong()+(UInfo.getAnsResult()==0?0:1));
					map.put(kqinfo.getKnoeledgeId(), pairs);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		double yuzhi = Double.parseDouble(PropertiesUtil.getString(GlobalValues.ProPerties_GoodBadKnowledgesYuZhi));
		List<String>goodList=new ArrayList<String>();
		List<String>badList=new ArrayList<String>();
		double pro = Double.parseDouble(PropertiesUtil.getString(GlobalValues.ProPerties_RandomAnswerRightPro));
		for(Map.Entry<Integer,Pairs> KEntry : map.entrySet())//计算知识点答题掌握程度
		{
			double scores = NumberUtil.GetCombination(KEntry.getValue().getRight()+KEntry.getValue().getWrong()
					,KEntry.getValue().getRight())*
					Math.pow(pro, KEntry.getValue().getRight())*
					Math.pow(1-pro, KEntry.getValue().getWrong());
			if(scores>yuzhi)
				badList.add(""+KEntry.getKey());
			else
				goodList.add(""+KEntry.getKey());
		}
		return ListUtil.listToStringJoinBySplit(goodList, ",")+";"+ListUtil.listToStringJoinBySplit(badList, ",");
	}

	@Override
	public Serializable GetRecommendsQuestions(List<Integer> SelectKnoledges,
			List<UserAnswerLogInfo> answerLogs,
			List<Integer>badKnoledgesList,String level,Integer size) {
		if(badKnoledgesList==null)
		{
			String badgoodknoledges = (String) CheckUserGoodBadKnowledges(answerLogs,SelectKnoledges);
			if(badgoodknoledges==null)return null;
			List<String> badstring = ListUtil.OverridStringSplit(badgoodknoledges, ';');
			if(badstring.size()<=1||StringUtils.isBlank(badstring.get(1)))return null;
			List<String>badidList =  ListUtil.stringsToListSplitBy(badstring.get(1), ",");
			for(String s : badidList){
				if(StringUtils.isBlank(s)&&StringUtils.isNumeric(s))
					badKnoledgesList.add(Integer.parseInt(s));
			}
		}
		try {
			List<QuestionInfo>qlist = (List<QuestionInfo>) questService.getQuestionsByRandom(badKnoledgesList,level, size,"q.flag=0");
			StringBuffer sb = new StringBuffer();
			if(qlist!=null)
			{
				for(QuestionInfo qinfo : qlist)
				{
					sb.append(qinfo.getId()+",");
				}
				if(sb.length()>1)
					return sb.substring(0,sb.length()-1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Serializable GetMidKnowledges(List<UserAnswerLogInfo> answerLogs,
			List<Integer> SelectKnoledgesID) {
		return null;
	}

	@Override
	public Serializable GetLearnLevel(List<UserAnswerLogInfo> answerLogs) {
		if(answerLogs==null||answerLogs.size()<=0)return null;
		double right_num=0.0;
		for(UserAnswerLogInfo info : answerLogs)
		{
			right_num=right_num+(info.getAnsResult()==0?1.0:0.0);
		}
		double big = Double.parseDouble(PropertiesUtil.getString("LearnLevelBigYuZhi"));
		double small = Double.parseDouble(PropertiesUtil.getString("LearnLevelSmallYuZhi"));
		if(right_num/answerLogs.size()>big)return 2;
		else if(right_num/answerLogs.size()>small)return 1;
		else return 0;
	}

	@Override
	public Serializable GetUserSortByScore(List<UserAnswerLogInfo> answerLogs) {
		if(answerLogs==null||answerLogs.size()<=0)return null;
		List<String>qids=new ArrayList<String>();
		double totlescore=answerLogs.size();
		double userscore=0.0;
		for(UserAnswerLogInfo info : answerLogs)
		{
			qids.add(info.getQid()+"");
			if(info.getAnsResult()==0)
				userscore+=1.0;
		}
		List<QuestionInfo>qinfo_list = (List<QuestionInfo>) UtilDao.useIDStringToList(new QuestionInfo(), ListUtil.listToStringJoinBySplit(qids, ","), ",");
		double avgScore=0.0;
		for(QuestionInfo temp_info : qinfo_list)
		{
			if(temp_info.getRightCount()+temp_info.getWrongCount()==0)
				avgScore+=0.5;
			else
			avgScore+=(double)temp_info.getRightCount()/(double)(temp_info.getRightCount()+temp_info.getWrongCount());
		}
		double level1=avgScore+1.0/6.0*(totlescore-avgScore);
		if(totlescore-avgScore==0)
				level1=avgScore;
		double level2=avgScore-1.0/6.0*avgScore;
		if(level2<=0)
			level2=0;
		if(userscore>=level1)return 2;
		else if(userscore>=level2)return 1;
		else return 0;
	}

	@Override
	public Serializable GetUserSortByAvgTime(List<UserAnswerLogInfo> answerLogs) {
		if(answerLogs==null||answerLogs.size()<=0)return null;
		List<String>qids=new ArrayList<String>();
		double userTime=0.0;
		for(UserAnswerLogInfo info : answerLogs)
		{
			qids.add(info.getQid()+"");
			userTime+=info.getUsedTime();
		}
		List<QuestionInfo>qinfo_list = (List<QuestionInfo>) UtilDao.useIDStringToList(new QuestionInfo(), ListUtil.listToStringJoinBySplit(qids, ","), ",");
		double avgTime=0.0;
		for(QuestionInfo temp_info : qinfo_list)
		{
			avgTime+=(double)temp_info.getAvgTime();
		}
		double level2=avgTime+1.0/6.0*avgTime;
		double level1=avgTime-1.0/6.0*avgTime;
		if(level1<=0)
			level1=0;
		if(userTime>=level1)return 2;
		else if(userTime>=level2)return 1;
		else return 0;
	}
}
