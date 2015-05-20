package com.ccc.test.service.interfaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.TagInfor;


public interface IQuestionService {

	/**上传题目
	 * @param file 题目的图片文件
	 * @param knowledges 题目的知识点字符串，用，隔开
	 * @param answer 题目的答案
	 * @param level 题目的难度
	 * @return 返回题目的id，否则返回错误信息
	 */
	Serializable uploadQuestion(HttpServletRequest req,String
			knowledges,String answer,String level) throws Exception;
	/**
	 * author cxl
	 * 上传试卷中的题目和对应的tag标记
	 * @param questionInfo
	 * @return
	 * @throws Exception
	 */
	Serializable uploadPaperQuest(List<QuestionInfo> questionInfos) throws Exception;
	/**
	 * author cxl
	 * 将题目和知识点对应关系写入数据表中
	 * @param questionInfos 
	 * @return
	 * @throws Exception 
	 */
	Serializable uploadQuestKnowledge(List<QuestionInfo> questionInfos) throws Exception;
//	Serializable fetchCandicateQuestion(ArrayList<Integer> knowlegIDList);
}
