package com.ccc.test.service.interfaces;

import java.io.Serializable;
import java.util.List;

import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;

public interface IAlgorithmService{

	/**
	 * 
	 * @param answerLogs 用户答题记录
	 * @param SelectKnoledges 用户选择的知识点
	 * @return 一个String 格式：good知识点1,good知识点2;bad知识点1,bad知识点2
	 */
	public Serializable CheckUserGoodBadKnowledges(List<UserAnswerLogInfo> answerLogs,
			List<KnowledgeInfo>SelectKnoledges);
}
