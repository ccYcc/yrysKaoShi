package com.ccc.test.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccc.test.service.interfaces.IPaperService;

/**
 * @author cxlTest
 *
 */
@Controller
@RequestMapping("/test")
public class TestPaperController {
	
	
	@Autowired
	IPaperService paperService;

	/**上传paper方法
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/uploadPaper")
	public String uploadPaper(
			HttpServletRequest request,
			HttpServletResponse response,
			Integer teacher_id ,
			String url,
			String question_ids)
	{
		
		   teacher_id = 1;
		   url="/test";
		   question_ids="1,2,3";
		   System.out.println("TEST UPLOAD_PAPER");
			try {
				Serializable ret = paperService.uploadPaper(request, teacher_id, url, question_ids);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return "adminMain";
	}

}
