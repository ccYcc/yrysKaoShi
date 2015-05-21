package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.hibernate.dao.interfaces.IknowledgeDao;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.KnowledgeQuestionRelationInfo;
import com.ccc.test.pojo.KnowledgeRelationInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.service.interfaces.IKnowledgeService;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;

public class KnowledgeServiceImpl implements IKnowledgeService{

	@Autowired
	IknowledgeDao knowledgeDao;
	
	@Override
	public Serializable getKnowlegeByID(Integer id) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put(KnowledgeInfo.COLUMN_ID, id);
//		MsgInfo msg = new MsgInfo();
//		List<KnowledgeInfo> knowledges = knowledgeDao.getList(map);
//		if ( ListUtil.isEmpty(knowledges) ){
//			msg.setMsg(GlobalValues.CODE_EMPTY_INPUT
//					, GlobalValues.MSG_EMPTY_INPUT);
//			return msg;
//		} else
		if(id!=null&&id<0)return null;
		List<KnowledgeInfo> knowledges = null;
		knowledges=knowledgeDao.getChild(id);
		for(KnowledgeInfo info : knowledges)
		{
			List<KnowledgeInfo> knowledges_ch = knowledgeDao.getChild(info.getId());
			if(knowledges_ch.size()>0)
				info.setHasChildren(true);
			else
				info.setHasChildren(false);
		}
		return (Serializable)knowledges;
	}

}
