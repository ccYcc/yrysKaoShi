package com.ccc.test.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.DataCollectInfo;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.KnowledgeQuestionRelationInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;
import com.ccc.test.service.interfaces.IDataCollectService;

public class IDataCollectServiceImpl implements IDataCollectService {

	@Autowired
	IBaseHibernateDao<DataCollectInfo> dataCollectDao;
	
	@Autowired
	IBaseHibernateDao<KnowledgeInfo> knowledgeDao;
	
	@Autowired
	IBaseHibernateDao<KnowledgeQuestionRelationInfo> knowledge_question_Dao;
	
	@Override
	public List<DataCollectInfo> getUserAllHistroyCollectData(
			List<UserAnswerLogInfo> qList) {
		
		if(qList==null||qList.size()<=0)return null;
		
		List<DataCollectInfo> currentData = getUserCollectData(qList);
		
		Map<Integer,DataCollectInfo>check_map = new HashMap<Integer, DataCollectInfo>();
		for(DataCollectInfo infos : currentData)
			check_map.put(infos.getKid(), infos);
				
		List<DataCollectInfo> reslut=new ArrayList<DataCollectInfo>();
		
		Map<String,Object> args=new HashMap<String,Object>();
		Integer userId = qList.get(0).getUid();
		args.put(DataCollectInfo.COLUMN_uid, userId);
		try {
			List<DataCollectInfo> dataInfoList = dataCollectDao.getList(args);
			for(DataCollectInfo history : dataInfoList)
			{
				DataCollectInfo curr = check_map.get(history.getKid());
				if(curr == null)
					continue;
				history.Add(curr);
				dataCollectDao.update(history);
				reslut.add(history);
				check_map.remove(history.getKid());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Map.Entry<Integer,DataCollectInfo> save : check_map.entrySet())
		{
			try {
				dataCollectDao.add(save.getValue());
				reslut.add(save.getValue());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return reslut;
	}


	@Override
	public List<DataCollectInfo> getUserCollectData(
			List<UserAnswerLogInfo> qList) {
		Map<Integer,DataCollectInfo> map =new HashMap<Integer, DataCollectInfo>();
		Map<String,Object> args=new HashMap<String,Object>();
		for(UserAnswerLogInfo UInfo : qList)
		{
			args.clear();
			args.put(KnowledgeQuestionRelationInfo.COLUMN_QuestionID, UInfo.getQid());
			try {
				List<KnowledgeQuestionRelationInfo> kList = knowledge_question_Dao.getList(args);
				if(kList==null||kList.size()<=0)
					continue;
				for(KnowledgeQuestionRelationInfo KTemp : kList)
				{
					DataCollectInfo dinfo = map.get(KTemp.getKnoeledgeId());
					if(dinfo==null)
					{
						dinfo=new DataCollectInfo();
						dinfo.setAns_number(0);
						dinfo.setUse_time(0);
						dinfo.setAns_right_number(0);
					}
					dinfo.setAns_number(1+dinfo.getAns_number());
					dinfo.setKid(KTemp.getKnoeledgeId());
					dinfo.setUid(UInfo.getUid());
					dinfo.setUse_time(dinfo.getUse_time()+UInfo.getUsedTime());
					dinfo.setAns_right_number(dinfo.getAns_right_number()+UInfo.getAnsResult()==0?1:0);
					map.put(KTemp.getKnoeledgeId(), dinfo);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<DataCollectInfo> reslut = new ArrayList<DataCollectInfo>();
		for(Map.Entry<Integer,DataCollectInfo> map_value : map.entrySet())
		{
			DataCollectInfo infos = map_value.getValue();
			try {
				infos.setK_name(GetKnowledgeName(infos.getKid()));
				reslut.add(infos);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return reslut; 
	}

	private String GetKnowledgeName(Integer id)
	{
		KnowledgeInfo kinfo;
		try {
			kinfo = knowledgeDao.getById(id);
			if(kinfo==null)return null;
			return kinfo.getName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
