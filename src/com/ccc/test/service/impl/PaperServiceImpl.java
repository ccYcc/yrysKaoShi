package com.ccc.test.service.impl;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.PaperInfo;
import com.ccc.test.service.interfaces.IPaperService;

public class PaperServiceImpl implements IPaperService{

	@Autowired
	IBaseHibernateDao<PaperInfo> paperDao ;
	@Override
	public Serializable uploadPaper(HttpServletRequest req, Integer teacher_id,
			String paper_url, String question_ids) throws Exception {
		// TODO Auto-generated method stub
		PaperInfo paperInfo = new PaperInfo();
		paperInfo.setPaperUrl(paper_url);
		paperInfo.setQuestionIds(question_ids);
		paperInfo.setTeacher_id(teacher_id);
		Serializable ret = paperDao.add(paperInfo);
		Integer uid = (Integer) ret;
		return true;
	}


	
}
