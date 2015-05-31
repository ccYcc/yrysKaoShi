package com.ccc.test.service.interfaces;

import java.util.List;

import com.ccc.test.pojo.DataCollectInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;

public interface IDataCollectService {

	/**
	 * qList 当前答题列表
	 * 获取用户历史统计数据
	 * 以知识点为标准统计
	 */
	public List<DataCollectInfo> getUserAllHistroyCollectData(List<UserAnswerLogInfo> qList);
	
	/**
	 * qList 当前答题列表
	 * 获取用户当前统计数据
	 * 以知识点为标准统计
	 * 注意 ： 这里未保存数据
	 */
	public List<DataCollectInfo> getUserCollectData(List<UserAnswerLogInfo> qList);
}
