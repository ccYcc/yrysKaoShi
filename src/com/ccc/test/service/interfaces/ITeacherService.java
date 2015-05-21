package com.ccc.test.service.interfaces;

import java.io.Serializable;

/**
 * @author cxl
 * 有关老师的业务
 *
 */
public interface ITeacherService {
	
	/**
	 * author cxl
	 * 老师上传试卷信息
	 * @param paper_url 试卷在服务器上存放的相对url
	 * @param paperName 试卷名称
	 * @param create_time 试卷上传时的时间
	 * @param question_ids 试卷中的题目id
	 * @param teacher_id 老师id
	 * @return
	 * @throws Exception
	 */
	 
	Serializable uploadPaper(String paper_url,
							 String paperName,
							 Long create_time,
							 String question_ids,
							 Integer teacher_id) throws Exception;
	
}
