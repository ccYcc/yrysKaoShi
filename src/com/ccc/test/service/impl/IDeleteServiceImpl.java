package com.ccc.test.service.impl;

import java.util.List;

import com.ccc.test.pojo.DiyPaperInfo;
import com.ccc.test.pojo.GroupInfo;
import com.ccc.test.pojo.HuoyueDayInfo;
import com.ccc.test.pojo.HuoyueMonthInfo;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.KnowledgeQuestionRelationInfo;
import com.ccc.test.pojo.PaperGroupRelationInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.TeacherPaperInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;
import com.ccc.test.pojo.UserGroupRelationInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.service.interfaces.IDeleteService;
import com.ccc.test.utils.UtilDao;
import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;

public class IDeleteServiceImpl implements IDeleteService {

	@Override
	public void DeleteAllUserDatas() {
		// TODO Auto-generated method stub
		try {
			UtilDao.DeleteAllDatas(new UserInfo());
			UtilDao.DeleteAllDatas(new UserAnswerLogInfo());
			UtilDao.DeleteAllDatas(new UserGroupRelationInfo());
			UtilDao.DeleteAllDatas(new TeacherPaperInfo());
			UtilDao.DeleteAllDatas(new DiyPaperInfo());
			UtilDao.DeleteAllDatas(new GroupInfo());
			UtilDao.DeleteAllDatas(new HuoyueDayInfo());
			UtilDao.DeleteAllDatas(new HuoyueMonthInfo());
			UtilDao.DeleteAllDatas(new PaperGroupRelationInfo());
			UtilDao.DeleteAllDatas(new ValidatedInfo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void DeleteAllQuestions() {
		// TODO Auto-generated method stub
		try {
			UtilDao.DeleteAllDatas(new QuestionInfo());
			UtilDao.DeleteAllDatas(new KnowledgeQuestionRelationInfo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void DeleteAllKnowledages() {
		// TODO Auto-generated method stub
		try {
			UtilDao.DeleteAllDatas(new KnowledgeInfo());
			DeleteAllQuestions();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public <T> List<T> FindPojoBy_Like_Names(T t, String likeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> void DeleteById(T t, Integer id) {
		// TODO Auto-generated method stub
		
	}


}
