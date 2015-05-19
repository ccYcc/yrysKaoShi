package com.ccc.test.service.interfaces;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public interface IPaperService {

	/**老师上传试卷
	 * @param teacher_id 上传老师的id号
	 * @param paper_url 试卷保存路径
	 * @param question_ids 题目id列表
	 * @return 返回题目的id，否则返回错误信息
	 */
	Serializable uploadPaper(HttpServletRequest req,int teacher_id,
			String paper_url,String question_ids) throws Exception;
	
}
