package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.List;

import com.ccc.test.pojo.UserAnswerLogInfo;
import com.ccc.test.service.interfaces.IPaperService;

public class PaperServiceImpl implements IPaperService {

	@Override
	public Serializable writeAnsLogs(List<UserAnswerLogInfo> answerLogs) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Serializable updateQuestion() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Serializable fetchUserPaper(Integer paperId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable createPaper(String knowledges, 
									String paperName,
									String logs,
									Long useTime, 
									Integer userId,
									String paperLevel,
									String learnLevel,
									String goodKnowledges,
									String badKnowledges,
									String midKnowledges,
									String recommendsQuestions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable fetchUserPaperList(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
