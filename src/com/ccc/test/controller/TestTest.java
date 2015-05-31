package com.ccc.test.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccc.test.pojo.DataCollectInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;
import com.ccc.test.service.interfaces.IDataCollectService;
import com.ccc.test.utils.Bog;

@Controller
@RequestMapping("/TTest")
public class TestTest {

	@Autowired
	IDataCollectService dataCollectService;
	
	@RequestMapping("/Data")
	public String quitGroup(HttpServletRequest request,
			   HttpServletResponse response,
			   Integer groupId,
			   Integer requestId) throws Exception
	{
		List<UserAnswerLogInfo> qList = new ArrayList<UserAnswerLogInfo>();
		for(int i=0;i<3;i++)
		{
			UserAnswerLogInfo info =new UserAnswerLogInfo();
			info.setUid(0);
			info.setAnsResult((int)(Math.random()*2.0));
			info.setQid((int)(Math.random()*5.0)+1);
			info.setUsedTime(1);
			qList.add(info);
		}
		for(UserAnswerLogInfo d : qList )
		{
			Bog.print(d.getQid()+"\t"+d.getUsedTime());
		}
		List<DataCollectInfo> data = dataCollectService.getUserAllHistroyCollectData(qList);
		Bog.print("===============");
		for(DataCollectInfo d : data )
		{
			Bog.print(d.getK_name()+"\t"+d.getAns_number());
		}
		return "";
	}
}
