package com.ccc.test.service.interfaces;

import java.io.Serializable;
import java.util.List;

import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;

public interface IPaperService {
	
	/**将回答记录写入数据库
	 * @author cxl
	 * @param answerLogs
	 * @return List<Integer> logIds flase:MsgInfo
	 */
	Serializable writeAnsLogs(List<UserAnswerLogInfo> answerLogs);
	
	/**创建试卷，将学生的答题情况，评估报告，推荐资源写入数据库
	 * @author cxl
	 * @param knowledges
	 * @param paperName
	 * @param logIds
	 * @param useTime
	 * @param rightCounts
	 * @param wrongCounts
	 * @param paperLevel
	 * @param learnLevel
	 * @param goodKnowledges
	 * @param badKnowledges
	 * @param midKnowledges
	 * @param recommendsQuestions
	 * @return paperId false:MsgInfo
	 */
	Serializable createPaper(List<KnowledgeInfo> knowledges,
							 String paperName,
							 List<Integer> logIds,
							 Long useTime,
							 Integer rightCounts,
							 Integer wrongCounts,
							 String paperLevel,
							 String learnLevel,
							 String goodKnowledges,
							 String badKnowledges,
							 String midKnowledges,
							 String recommendsQuestions);
	
	Serializable fetchPaper(Integer paperId);
}
