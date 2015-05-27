package com.ccc.test.exception;

import java.util.Map;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class SimpleHandleException implements IHandleException{

	@Override
	public void handle(Exception e, Map model) {
		if ( e != null ){
			if ( model == null ){
				e.printStackTrace();
				return;
			}
			String msg = e.getMessage().replaceAll("\n", "");
			msg = msg == null ? "" : msg;
			if ( msg.contains("JDBC") ){
				model.put("result","数据库连接失败！");
			} else {
				model.put("result","发生错误 "+msg);
			}
		} else {
			model.put("result","发生未知错误");
		}
	}

	public void wrapModelMapInRedirectMap(RedirectAttributes ra , Map map){
		ra.addFlashAttribute("result", map.get("result"));
	}
}
