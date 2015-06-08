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

import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.json.JsKnowledgeTreeNode;
import com.ccc.test.service.interfaces.IKnowledgeService;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.StringUtil;

@Controller
public class KnowledgesController {

	@Autowired
	IKnowledgeService kService;
	
	/** 上传知识点
	 * @param request
	 * @param file
	 * @param model
	 * @return
	 */
	@RequestMapping("/knowledge/uploadKnowledge")
	public String uploadKnowledge(
			HttpServletRequest request,
			@RequestParam CommonsMultipartFile file,
			ModelMap model
			){
		
		try {
			Serializable ret = kService.uploadKnowledge(request);
			if ( ret == null || StringUtil.isEmpty((String) ret) ){
				model.addAttribute("result", "上传成功");
			} else {
				model.addAttribute("result", ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "adminMain";
	}
	
	@ResponseBody
	@RequestMapping("/json/getKnowledges")
	public  List<JsKnowledgeTreeNode> getKnowledgesJson(int id,ModelMap model){
		Bog.print(id+"");
		List<JsKnowledgeTreeNode> ret = new ArrayList<JsKnowledgeTreeNode>();
		Integer kid = id < 0 ? null : id;
		try {
			Serializable ks = kService.getKnowlegeByID(kid);
			if ( ks instanceof List ){
				List<KnowledgeInfo> children = (List<KnowledgeInfo>) ks;
				for ( KnowledgeInfo k : children ){
					JsKnowledgeTreeNode bean = new JsKnowledgeTreeNode();
					ret.add(bean.dataFromKnowledge(k));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
