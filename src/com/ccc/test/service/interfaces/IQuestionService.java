package com.ccc.test.service.interfaces;

import java.io.Serializable;
import java.util.Map;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

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
	
	public static final int knowledge_index=3;
	public static final int answer_index=1;
	public static final int image_name_index=0;
	public static final int level_index=2;

	Serializable uploadQuestion(HttpServletRequest req,String
			knowledges,String answer,String level) throws Exception;
	
//	Serializable fetchCandicateQuestion(ArrayList<Integer> knowlegIDList);

}
