package com.ccc.test.service.interfaces;

import java.io.Serializable;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.TagInfor;
import com.ccc.test.pojo.UserAnswerLogInfo;


public interface IQuestionService {

	/**上传题目
	 * @param args_map 上传的题目的所有参数，key值为ARG_KNOWLEDGES，ARG_ANSWER，ARG_image_name，ARG_level,ARG_Image_URL
	 * KNOWLEDGES value为List<String>
	 * @return 返回题目的id，否则返回错误信息
	 */
	public String SaveAQuestionByUpLoad(Map<String,Object>args_map);
	
	public static final String ARG_KNOWLEDGES="knowledge";
	public static final String ARG_ANSWER="answer";
	public static final String ARG_image_name="image_name";
	public static final String ARG_level="level";
	public static final String ARG_Image_URL="URL";
	public static final String ARG_options="options";
	
	public static final int knowledge_index=4;
	public static final int answer_index=1;
	public static final int image_name_index=0;
	public static final int level_index=3;
	public static final int options_index=2;
	
	public static final String category="resources/questions";
	
	public Serializable uploadQuestion(HttpServletRequest req,String
			knowledges,String answer,String level) throws Exception;
	/**
	 * author cxl
	 * 上传试卷中的题目和对应的tag标记
	 * @param questionInfo
	 * @return MsgInfo
	 * @throws Exception
	 */
	Serializable uploadPaperQuest(List<QuestionInfo> questionInfos) throws Exception;
	/**
	 * author cxl
	 * 将题目和知识点对应关系写入数据表中
	 * @param questionInfos 
	 * @return MsgInfo
	 * @throws Exception 
	 */
	Serializable uploadQuestKnowledge(List<QuestionInfo> questionInfos) throws Exception;
//	Serializable fetchCandicateQuestion(ArrayList<Integer> knowlegIDList);

	/**
	 * author ycc
	 * 获取随机的几个Questions
	 * @param knowledges 知识点
	 * @param level 难度
	 * @param size 返回问题多少 
	 * @return 一个list《Questions》
	 * @throws Exception 
	 */
	public Serializable getQuestionsByRandom(Integer knowledges,String level, int size) throws Exception;
	
//	/**
//	 * author ycc
//	 * 由算法生成获取Questions
//	 * @param knowledges 知识点
//	 * @param level 难度 
//	 * @return 一个Questions 如果没有了 返回null
//	 * @throws Exception 
//	 */
//	public Serializable getOneQuestionsByMethod(Integer knowledges,String level) throws Exception;
	
	/**
	 * author ycc
	 * 获取随机的几个Questions
	 * @param knowledges 一列知识点
	 * @param level 难度
	 * @param size 返回问题多少 
	 * @return 一个list《Questions》
	 * @throws Exception 
	 */
	public Serializable getQuestionsByRandom(List<Integer> knowledges,String level, int size) throws Exception;
	
	/**
	 * author ycc
	 * 由算法生成获取Questions
	 * @param knowledges 一列知识点
	 * @param level 难度
	 * @param  AnswerLogList log列表
	 * @return 一个Questions 如果没有了 返回null
	 * @throws Exception 
	 */
	public Serializable getOneQuestionsByMethod(List<Integer> knowledges,String level,
			List<UserAnswerLogInfo>AnswerLogList,HttpSession session) throws Exception;
	public Serializable getOneQuestionsByMethodGloble(List<Integer> knowledges,
			String level,List<UserAnswerLogInfo>AnswerLog,HttpSession session) throws Exception;
	/**
	 * 根据AnswerLogInfo获取题目
	 * @param loginfo
	 * @return
	 */
	public Serializable GetQuestionFromAnswerLog(UserAnswerLogInfo loginfo);
	
	/**
	 * author ycc
	 * 获取随机的几个Questions
	 * @param knowledges 一列知识点
	 * @param level 难度
	 * @param size 返回问题多少
	 * @param condition附加条件，没有可设为空 
	 * @return 一个list《Questions》
	 * @throws Exception 
	 */
	public Serializable getQuestionsByRandom(List<Integer> knowledges,String level, int size,String condition) throws Exception;
	
}
