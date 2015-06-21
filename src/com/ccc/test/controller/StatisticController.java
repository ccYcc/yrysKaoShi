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
import com.ccc.test.utils.TimeUtil;

/**活跃用户统计控制器
 * @author Trible Chen
 *
 */
@Controller
public class StatisticController {


	@Autowired
	IUserStatisticService statisticService;
	
	/**获取某日各个时段活跃用户信息
	 * @param daytime
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/json/getHuoyueOfDay")
	public  Serializable getHuoyueOfDayJson(long daytime,ModelMap model){
		try {
			Bog.print("getHuoyueOfDayJson"+TimeUtil.format(daytime, "yyyy/MM/dd HH:mm:ss"));
			if ( daytime == 0 ){
				daytime = System.currentTimeMillis();
			}
			Serializable map = statisticService.getHuoyueNumOfDay(daytime);
			if (map instanceof Map){
				Map<String, Integer> result = (Map<String, Integer>) map;
				return (Serializable) result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**获取某月活跃用户统计信息
	 * @param daytime
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/json/getHuoyueOfMonth")
	public  Serializable getHuoyueOfMonthJson(long daytime,ModelMap model){
		try {
			Bog.print("getHuoyueOfMonthJson"+TimeUtil.format(daytime, "yyyy/MM/dd HH:mm:ss"));
			if ( daytime == 0 ){
				daytime = System.currentTimeMillis();
			}
			Serializable map = statisticService.getHuoyueNumOfMonth(daytime);
			
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
	
	/**活跃某月注册人数统计信息
	 * @param daytime
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/json/getRegisterOfMonth")
	public  Serializable getRegisterOfMonthJson(long daytime,ModelMap model){
		try {
			Bog.print("getRegisterOfMonthJson"+TimeUtil.format(daytime, "yyyy/MM/dd HH:mm:ss"));
			if ( daytime == 0 ){
				daytime = System.currentTimeMillis();
			}
			Serializable map = statisticService.getRegisterNumOfMonth(daytime);
			
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
