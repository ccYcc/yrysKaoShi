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
			List<Integer>SelectKnoledgesID);
	
	/**
	 * 根据用户位掌握知识点随机推荐该知识点的题目
	 * @param SelectKnoledges 知识点id
	 * @param answerLogs 用户答题记录
	 * @param badKnoledgesList 薄弱知识点
	 * @param level 难度
	 * @param size 返回题目个数
	 * @return id列表
	 */
	public Serializable GetRecommendsQuestions(List<Integer> SelectKnoledges,List<UserAnswerLogInfo> answerLogs,
			List<Integer>badKnoledgesList,String level,Integer size);
	
	/**
	 * 暂时不用
	 * @param 
	 * @param 
	 * @return null
	 */
	public Serializable GetMidKnowledges(List<UserAnswerLogInfo> answerLogs,
			List<Integer>SelectKnoledgesID);
	
	/**
	 * 暂时不用
	 * @param 
	 * @return Integer null,0,1,2  分别表示:错误,下,中,上游
	 */
	public Serializable GetLearnLevel(List<UserAnswerLogInfo> answerLogs);
	
	/**
	 * 用回答记录获取用胡答题排名
	 * @param answerLogs 答题记录
	 * @return Integer null,0,1,2  分别表示:错误,下,中,上游
	 */
	public Serializable GetUserSortByScore(List<UserAnswerLogInfo> answerLogs);
	
	/**
	 * 用回答记录获取用胡答题排名
	 * @param answerLogs 答题记录
	 * @return Integer null,0,1,2  分别表示:错误,下,中,上游
	 */
	public Serializable GetUserSortByAvgTime(List<UserAnswerLogInfo> answerLogs);
}
