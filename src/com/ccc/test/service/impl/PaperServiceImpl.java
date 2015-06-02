package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.List;

import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;
import com.ccc.test.service.interfaces.IPaperService;

public class PaperServiceImpl implements IPaperService {

	@Override
	public Serializable writeAnsLogs(List<UserAnswerLogInfo> answerLogs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable createPaper(List<KnowledgeInfo> knowledges,
			String paperName, List<Integer> logIds, Long useTime,
			Integer rightCounts, Integer wrongCounts, String paperLevel,
			String learnLevel, String goodKnowledges, String badKnowledges,
			String midKnowledges, String recommendsQuestions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable fetchPaper(Integer paperId) {
		// TODO Auto-generated method stub
		return null;
	}

}
