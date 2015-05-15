package com.ccc.test.service.interfaces;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public interface IQuestionService {

	/**上传题目
	 * @param file 题目的图片文件
	 * @param knowledges 题目的知识点字符串，用，隔开
	 * @param answer 题目的答案
	 * @param level 题目的难度
	 * @return 返回题目的id，否则返回错误信息
	 */
	Serializable uploadQuestion(HttpServletRequest req,String knowledges,String answer,String level) throws Exception;
}
