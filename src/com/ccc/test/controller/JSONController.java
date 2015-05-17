package com.ccc.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccc.test.pojo.KnowledgeInfo;

@Controller
@RequestMapping("/json")
public class JSONController {

	@RequestMapping("/getKnowledges")
	public @ResponseBody String getKnowledges(String id){
		System.out.println("getKnowledges id="+id);
		return "["+"{ 'id' : 'ajson1', 'parent' : '#', 'text' : 'Simple root node' },"
				+"{ 'id' : 'ajson2', 'parent' : '#', 'text' : 'Root node 2' },"
				+"{ 'id' : 'ajson3', 'parent' : 'ajson2', 'text' : 'Child 1' },"
				+"{ 'id' : 'ajson4', 'parent' : 'ajson2', 'text' : 'Child 2' },"
				+"]";
	}
}
