package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.KnowledgeQuestionRelationInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;
import com.ccc.test.service.interfaces.IAlgorithmService;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.NumberUtil;
import com.ccc.test.utils.PropertiesUtil;
import com.ccc.test.utils.StringUtil;

public class IAlgorithmServiceImpl implements IAlgorithmService {

	@Autowired
	IBaseHibernateDao<QuestionInfo> questDao;
	
	@Autowired
	IBaseHibernateDao<KnowledgeInfo> knowledgeDao;
	
	@Autowired
	IBaseHibernateDao<KnowledgeQuestionRelationInfo> knowledge_question_Dao;
	
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
			List<KnowledgeInfo> SelectKnoledges) {
		
		if(SelectKnoledges==null||SelectKnoledges.size()<=0)return null;
		if(answerLogs==null||answerLogs.size()<=0)return null;
		
		Map<Integer,Pairs>map=new HashMap<Integer,Pairs>();//key：知识点id，value：对与错个数
		for(KnowledgeInfo KInfo : SelectKnoledges)
			map.put(KInfo.getId(), new Pairs(0,0));
		
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
					if(pairs!=null)
					{
						pairs.setRight(pairs.getRight()+(UInfo.getAnsResult()==0?1:0));
						pairs.setWrong(pairs.getWrong()+(UInfo.getAnsResult()==0?0:1));
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		double yuzhi = Double.parseDouble(PropertiesUtil.getString(GlobalValues.ProPerties_YuZhi));
		String bad="bad";
		String good="good";
		for(Map.Entry<Integer,Pairs> KEntry : map.entrySet())//计算知识点答题掌握程度
		{
			double scores = NumberUtil.GetCombination(KEntry.getValue().getRight()+KEntry.getValue().getWrong()
					,KEntry.getValue().getRight())*
					Math.pow(0.25, KEntry.getValue().getRight())*
					Math.pow(0.75, KEntry.getValue().getWrong());
			if(scores>yuzhi)
				bad+=","+KEntry.getValue();
			else
				good+=","+KEntry.getValue();
		}
		return bad+";"+good;
	}

}
