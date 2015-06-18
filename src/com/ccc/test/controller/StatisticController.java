package com.ccc.test.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.json.JsKnowledgeTreeNode;
import com.ccc.test.service.interfaces.IKnowledgeService;
import com.ccc.test.service.interfaces.IUserStatisticService;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.StringUtil;

/**活跃用户统计控制器
 * @author Trible Chen
 *
 */
@Controller
public class StatisticController {


	@Autowired
	IUserStatisticService statisticService;
	
	@ResponseBody
	@RequestMapping("/json/getHuoyueOfDay")
	public  Serializable getgetHuoyueOfDayJson(long daytime,ModelMap model){
		try {
			if ( daytime == 0 ){
				daytime = System.currentTimeMillis();
			}
			Serializable map = statisticService.getHuoyueNumOfDay(daytime);
			if (map instanceof Map){
				Map<String, Integer> result = (Map<String, Integer>) map;
				return (Serializable) result;
			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
