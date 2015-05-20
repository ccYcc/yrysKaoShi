package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.service.interfaces.IKnowledgeService;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;

public class KnowledgeServiceImpl implements IKnowledgeService{

	@Autowired
	IBaseHibernateDao<KnowledgeInfo> knowledgeDao;
	@Override
	public Serializable getKnowlegeByID(int id) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(KnowledgeInfo.COLUMN_ID, id);
		MsgInfo msg = new MsgInfo();
		List<KnowledgeInfo> knowledges = knowledgeDao.getList(map);
		if ( ListUtil.isEmpty(knowledges) ){
			msg.setMsg(GlobalValues.CODE_EMPTY_INPUT
					, GlobalValues.MSG_EMPTY_INPUT);
			return msg;
		} else {
			return (Serializable) knowledges;
		}
	}

}
