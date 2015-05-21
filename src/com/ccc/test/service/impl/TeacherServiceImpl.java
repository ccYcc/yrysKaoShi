package com.ccc.test.service.impl;

import java.io.Serializable;

import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.PaperInfo;
import com.ccc.test.service.interfaces.ITeacherService;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.UtilDao;

public class TeacherServiceImpl implements ITeacherService{

	@Override
	public Serializable uploadPaper(String paper_url,
									String paperName,
									Long create_time,
									String question_ids,
									Integer teacher_id) throws Exception {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		PaperInfo paperInfo = new PaperInfo();
		paperInfo.setPaperUrl(paper_url);
		paperInfo.setName(paperName);
	    paperInfo.setCreateTime(create_time);
		paperInfo.setQuestionIds(question_ids);
		paperInfo.setTeacher_id(teacher_id);
		Serializable ret = UtilDao.add(paperInfo);
		Integer uid = (Integer) ret;
		if( uid == -1 ){
			msg.setMsg(GlobalValues.CODE_ADD_FAILED
					, GlobalValues.MSG_FAILED);
		} else if ( uid > 0 ){
			msg.setMsg(GlobalValues.CODE_SUCCESS
					, GlobalValues.MSG_SUCCESS);
		}
		return msg;
	}
	
}
