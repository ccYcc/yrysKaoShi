package com.ccc.test.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ccc.test.pojo.JsTreeBean;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.service.interfaces.IKnowledgeService;
import com.ccc.test.utils.Bog;

@Controller
public class KnowledgesController {

	@Autowired
	IKnowledgeService kService;
	
	@RequestMapping("/knowledge/uploadKnowledge")
	public String uploadKnowledge(
			HttpServletRequest request,
			@RequestParam CommonsMultipartFile file,
			ModelMap model
			){
		
		try {
			
			Serializable ret = kService.uploadKnowledge(request);
			Bog.print((String)ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "adminMain";
	}
	
	@ResponseBody
	@RequestMapping("/json/getKnowledges")
	public  List<JsTreeBean> getKnowledgesJson(int id,ModelMap model){
		Bog.print(id+"");
		List<JsTreeBean> ret = new ArrayList<JsTreeBean>();
		Integer kid = id < 0 ? null : id;
		try {
			Serializable ks = kService.getKnowlegeByID(kid);
			if ( ks instanceof List ){
				List<KnowledgeInfo> children = (List<KnowledgeInfo>) ks;
				for ( KnowledgeInfo k : children ){
					JsTreeBean bean = new JsTreeBean();
					ret.add(bean.dataFromKnowledge(k));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		int childs = 4;
//		if ( id < 0 ){
////			获取根节点列表
//			for ( int i  = 1; i <= childs ; i++ ){
//				JsTreeBean bean = new JsTreeBean();
//				bean.setId(i);
//				bean.setText("知识点name"+i);
//				ret.add(bean);
//				if ( i % 2 == 0){
//					bean.setChildren(true);
//				}
//			}
//		} else {
////			根据id获取知识点列表
//			for ( int i  = 5; i <= childs+5 ; i++ ){
//				JsTreeBean bean = new JsTreeBean();
//				bean.setId(i*id);
//				bean.setText("知识点name"+i);
//				ret.add(bean);
//				if ( i % 2 == 0){
//					bean.setChildren(true);
//				}
//			}
//		}
		return ret;
	}
}
