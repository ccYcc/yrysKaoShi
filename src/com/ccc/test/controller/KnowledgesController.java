package com.ccc.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccc.test.pojo.JsTreeBean;

@Controller
public class KnowledgesController {

	@ResponseBody
	@RequestMapping("/json/getKnowledges")
	public  List<JsTreeBean> getKnowledgesJson(int id,ModelMap model){
		System.out.println(id);
		List<JsTreeBean> ret = new ArrayList<JsTreeBean>();
		int childs = 4;
		if ( id < 0 ){
//			获取根节点列表
			for ( int i  = 1; i <= childs ; i++ ){
				JsTreeBean bean = new JsTreeBean();
				bean.setId(i);
				bean.setText("知识点name"+i);
				ret.add(bean);
				if ( i % 2 == 0){
					bean.setChildren(true);
				}
			}
		} else {
//			根据id获取知识点列表
			for ( int i  = 5; i <= childs+5 ; i++ ){
				JsTreeBean bean = new JsTreeBean();
				bean.setId(i*id);
				bean.setText("知识点name"+i);
				ret.add(bean);
				if ( i % 2 == 0){
					bean.setChildren(true);
				}
			}
		}
		return ret;
	}
}
