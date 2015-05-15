package com.ccc.test.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.service.interfaces.IQuestionService;

public class QuestionServiceImpl implements IQuestionService{

	@Autowired
	IBaseHibernateDao<QuestionInfo> questDao;
	
	@Override
	public Serializable uploadQuestion(HttpServletRequest request, String knowledges,
			String answer, String level) throws Exception {
		
		String category = "questions";
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver 
        						= new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                int pre = (int) System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){
                	
                	
//                	questService.uploadQuestion(file, "", answer, level);
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(!"".equals(myFileName.trim())){  
                        System.out.println(myFileName);  
                        //重命名上传后的文件名  
                        String fileName = "demoUpload" + file.getOriginalFilename();  
                        //定义上传路径
                      //获取文件 存储位置 放在项目根目录
    	        		String realPath = request.getSession().getServletContext()
    	        				.getRealPath("/"+category);
    	        		File pathFile = new File(realPath);
    	        		if (!pathFile.exists()) {
    	        			//文件夹不存 创建文件
    	        			pathFile.mkdirs();
    	        		}
                        String path = pathFile.getAbsolutePath()+"/" + fileName;  
                        File localFile = new File(path);  
                        file.transferTo(localFile);
                        QuestionInfo quest = new QuestionInfo();
                        quest.setAnswer(answer);
                        quest.setLevel(level);
                        quest.setQuestionUrl("");
                        quest.setKnowledgeIds(knowledges);
                        questDao.add(quest);
                    }
                    break;
                }  
                //记录上传该文件后的时间  
                int finaltime = (int) System.currentTimeMillis();  
                System.out.println(finaltime - pre);  
            }
        }

		return null;
	}

	
}
