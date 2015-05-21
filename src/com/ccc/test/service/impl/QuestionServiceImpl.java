package com.ccc.test.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ccc.test.controller.FileController;
import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.KnowledgeQuestionRelationInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.service.interfaces.IFileService;
import com.ccc.test.service.interfaces.IQuestionService;
import com.ccc.test.utils.GlobalValues;
import com.sun.org.apache.bcel.internal.generic.IOR;

import net.lingala.zip4j.core.ZipFile;  
import net.lingala.zip4j.exception.ZipException;  
import net.lingala.zip4j.model.FileHeader;  
import net.lingala.zip4j.model.ZipParameters;  
import net.lingala.zip4j.util.Zip4jConstants;

public class QuestionServiceImpl implements IQuestionService{

	@Autowired
	IBaseHibernateDao<QuestionInfo> questDao;
	
	@Autowired
	IBaseHibernateDao<KnowledgeInfo> knowledgeDao;
	
	@Autowired
	IBaseHibernateDao<KnowledgeQuestionRelationInfo> knowledge_question_Dao;
	
	public static void main(String[] args) {
//		String ss="D:/tomcat/webapps/yrysKaoShi/questions/temp/";
//		QuestionServiceImpl qs=new QuestionServiceImpl();
//		try {
//			unzip(ss+"demoUploadDesktop.zip", ss,null);
//		} catch (ZipException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	private Serializable HandleZip(final String FileAddrs, final String temp_Out_addrs)
	{
//		try {
//			new Runnable() {
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					try {
//						unzip(FileAddrs,temp_Out_addrs,null);
//						HandleQuestions(temp_Out_addrs);
//					} catch (ZipException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}.run();
//			return true;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			System.out.println(e.toString());
//		}
		IFileService file = new FileServiceImpl();
		String message="";
		try {
			file.unzip(FileAddrs,temp_Out_addrs,null);
			List<String> fail_list = HandleQuestions(temp_Out_addrs);
			for(String fail : fail_list)
				message+=fail+"\n";
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
	private List<String> HandleQuestions(String DataPath)
	{
		File file=new File(DataPath);
		Set<String> Image_Path=new HashSet<String>();
		List<String>sql_Save_Question=new ArrayList<String>();
		List<String>fail_list=new ArrayList<String>();
		for(File f:file.listFiles())
		{
			if(f.getName().contains("png")||f.getName().contains("jpg"))
			{
				System.out.println("212"+f.getName());
				Image_Path.add(f.getName());
			}
		}
		for(File f:file.listFiles())
		{
			if(f.getName().contains("csv"))
			{
				System.out.println("333"+f.getName());
				try {
					BufferedReader br=new BufferedReader(new FileReader(f));
					String read=br.readLine();
					while((read=br.readLine())!=null)
					{
						String[] temp=read.split(",");
						boolean iscontinue=false;
						Map<String,Object> args_map=new HashMap<String,Object>();
						List<String> knowledge_list=new ArrayList<String>();
						for(int i=IQuestionService.knowledge_index;i<temp.length;i++)//check 知识点是否存在
						{
							if(temp.length<4||Image_Path.contains(temp[IQuestionService.image_name_index])==false){
								iscontinue=true;
								fail_list.add(read+"\t错误");
								break;
							}
							knowledge_list.add(temp[i]);
						}
						if(iscontinue)continue;
						args_map.put(IQuestionService.ARG_KNOWLEDGES, knowledge_list);
						args_map.put(IQuestionService.ARG_image_name, temp[IQuestionService.image_name_index]);
						args_map.put(IQuestionService.ARG_ANSWER, temp[IQuestionService.answer_index]);
						args_map.put(IQuestionService.ARG_level, temp[IQuestionService.level_index]);
						args_map.put(IQuestionService.ARG_Image_URL, f.getParent()+"/"+temp[IQuestionService.image_name_index]);
						args_map.put(IQuestionService.ARG_options, f.getParent()+"/"+temp[IQuestionService.options_index]);
						
						String res=SaveAQuestionByUpLoad(args_map);
						if(res!=null)
							fail_list.add(read+"\t"+res);
					}
				}  catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(e.toString());
					return fail_list;
				}
				break;
			}
		}
		return fail_list;
	}
	@Override
	public String SaveAQuestionByUpLoad(Map<String,Object>args_map)
	{
		List<String> knowledge_list=(List<String>) args_map.get(IQuestionService.ARG_KNOWLEDGES);
		String answer=(String) args_map.get(IQuestionService.ARG_ANSWER);
		String level=(String) args_map.get(IQuestionService.ARG_level);
		if(knowledge_list==null)
			return "知识点为空";
		Map<String,Integer>knowledge_id_map = new HashMap<String, Integer>();
		for(String knowledge_name : knowledge_list)//check 知识点是否存在
		{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("name", knowledge_name);
			try {
				List<KnowledgeInfo> knowlist=knowledgeDao.getList(map);
				if(knowlist==null||knowlist.size()<=0)
				{
					return "知识点："+knowledge_name+"不存在";
				}else
					knowledge_id_map.put(knowledge_name,knowlist.get(0).getId());
				
			} catch (Exception e) {
				System.out.println(e.toString()+"\n"+knowledgeDao.getClass().getName());
				return "知识点："+knowledge_name+"存储错误";
			}
		}
        QuestionInfo quest = new QuestionInfo();
        quest.setAnswer(answer);
        quest.setLevel(level);
        quest.setQuestionUrl((String)(args_map.get(IQuestionService.ARG_Image_URL)));
        try {
			Serializable res = questDao.add(quest);
			for(String knowledge_name : knowledge_list)
			{
				KnowledgeQuestionRelationInfo knowinfo=new KnowledgeQuestionRelationInfo();
				knowinfo.setKnoeledgeId(knowledge_id_map.get(knowledge_name));
				knowinfo.setQuestionId(quest.getId());
				knowledge_question_Dao.add(knowinfo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			return "知识点存储错误";
		}
		//sql_Save_Question.add("insert into tb_questioin")
		return null;
	}
	@Override
	public Serializable uploadQuestion(HttpServletRequest request, String knowledges,
			String answer, String level) throws Exception {
		
		MsgInfo msg = new MsgInfo();
		IFileService fileservice = new FileServiceImpl();
		String filePath = (String)fileservice.SaveUploadFile(request, "questions");
		
		if(!filePath.contains("zip")&&!filePath.contains("rar"))
        {
        	msg.setMsg(GlobalValues.CODE_UPLOAD_NOT_RIGHT_FORM
					, GlobalValues.MSG_UPLOAD_NOT_RIGHT_FORM);
        	return msg;
        }
        else
        {
        	String res = (String) HandleZip(filePath, filePath.substring(0, filePath.lastIndexOf("/")));
        	if(res!=null)
            {
            	msg.setMsg(GlobalValues.CODE_UPLOAD_UNZIP_FAIL
    					, res);
            	return msg;
            }
        }
		return null;
	}

	
}
