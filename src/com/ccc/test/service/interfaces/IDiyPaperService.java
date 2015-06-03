package com.ccc.test.service.interfaces;

import java.io.Serializable;
import java.util.List;

import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;

public interface IDiyPaperService {
	
	/**将回答记录写入数据库
	 * @author cxl
	 * @param answerLogs
	 * @return List<Integer> logIds flase:MsgInfo
	 */
	Serializable writeAnsLogs(List<UserAnswerLogInfo> answerLogs);
	
	
	/**
	 * 更新题目的信息：每个题目被答对的次数和答错得次数
	 * @return
	 */
	Serializable updateQuestion();
	
	/**创建试卷，将学生的答题情况，评估报告，推荐资源写入数据库
	 * @author cxl
	 * @param knowledges 学生所选知识点id列表，用逗号隔开
	 * @param paperName 学生为试卷所取的名称
	 * @param logs 回答记录id列表，用逗号隔开
	 * @param useTime 完成试卷用时
	 * @param paperLevel 试卷难度
	 * @param learnLevel 学生对该套试卷的掌握程度
	 * @param goodKnowledges 掌握的知识点id列表,用逗号隔开
	 * @param badKnowledges 没有掌握的知识点id列表，用逗号隔开
	 * @param midKnowledges 还需加强学习的知识点列表，用逗号隔开（暂时不用）
	 * @param recommendsQuestions 推荐题目id列表，用逗号隔开 
	 * @return paperId false:MsgInfo
	 */
	Serializable createPaper(String knowledges,
							 String paperName,
							 String logs,
							 Long useTime,
							 Integer userId,
							 String paperLevel,
							 String learnLevel,
							 String goodKnowledges,
							 String badKnowledges,
							 String midKnowledges,
							 String recommendsQuestions);
	
	/**
	 * 根据paperId获取MyPaperInfo
	 * @param paperId
	 * @return MyPaperInfo false：MsgInfo
	 */
	Serializable fetchUserPaper(Integer paperId);
	
	/**
	 * 根据学生id获取学生做过的所有试卷信息列表
	 * @param userId
	 * @return	List<MyPaperInfo> false:MsgInfo
	 */
	
	Serializable fetchUserPaperList(Integer userId);
}
