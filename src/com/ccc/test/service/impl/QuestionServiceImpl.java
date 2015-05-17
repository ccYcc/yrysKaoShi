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

import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.KnowledgeQuestionRelationInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.service.interfaces.IQuestionService;
import com.ccc.test.utils.GlobalValues;

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
	
	private void unzip(String zipFile, String dest, String passwd) throws ZipException {    
        ZipFile zFile = new ZipFile(zipFile);  // 首先创建ZipFile指向磁盘上的.zip文件    
        zFile.setFileNameCharset("GBK");       // 设置文件名编码，在GBK系统中需要设置    
        if (!zFile.isValidZipFile()) {   // 验证.zip文件是否合法，包括文件是否存在、是否为zip文件、是否被损坏等    
        	File file=new File(zipFile);
            file.delete();
        	throw new ZipException("压缩文件不合法,可能被损坏.");
        }    
        File destDir = new File(dest);     // 解压目录    
        if (destDir.isDirectory() && !destDir.exists()) {    
            destDir.mkdir();    
        }    
        if (zFile.isEncrypted()) {    
            zFile.setPassword(passwd.toCharArray());  // 设置密码    
        }    
        zFile.extractAll(dest);      // 将文件抽出到解压目录(解压)
        File file=new File(zipFile);
        file.delete();
    }  
	
	private boolean HandleZip(final String FileAddrs, final String temp_Out_addrs)
	{
		try {
			new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						unzip(FileAddrs,temp_Out_addrs,null);
						HandleQuestions(temp_Out_addrs);
					} catch (ZipException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.run();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}
		return false;
	}
	private void HandleQuestions(String DataPath)
	{
		int knowledge_index=3;
		int answer_index=1;
		int image_name_index=0;
		int level_index=2;
		
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
						Map<String,Object> knowledge_id_map=new HashMap<String, Object>();
						boolean iscontinue=false;
						for(int i=knowledge_index;i<temp.length;i++)//check 知识点是否存在
						{
							if(Image_Path.contains(temp[image_name_index])==false){
								
								iscontinue=true;
								fail_list.add(read+"\t图片名错误");
								break;
							}
							Map<String,Object> map=new HashMap<String,Object>();
							map.put("name", temp[i]);
							try {
								List<KnowledgeInfo> knowlist=knowledgeDao.getList(map);
								if(knowlist==null)
								{
									iscontinue=true;
									fail_list.add(read+"\t知识点："+temp[i]+"不存在");
									break;
								}else
									knowledge_id_map.put(temp[i],knowlist.get(0).getId());
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								System.out.println(e.toString());
							}
						}
						if(iscontinue)continue;
	                    QuestionInfo quest = new QuestionInfo();
	                    quest.setAnswer(temp[answer_index]);
	                    quest.setLevel(temp[level_index]);
	                    quest.setQuestionUrl(f.getParent()+"/"+temp[image_name_index]);
	                    try {
							Serializable res = questDao.add(quest);//could not fetch initial value for increment generator
							System.out.println(quest.getQuestionUrl()+"\t"+quest.getId());
							String kk_string="";
							for(int i=knowledge_index;i<temp.length;i++)
							{
								KnowledgeQuestionRelationInfo knowinfo=new KnowledgeQuestionRelationInfo();
								knowinfo.setKnoeledgeId(knowledge_id_map.get(temp[i]));
								knowinfo.setQuestionId(quest.getId());
								System.out.println("dsadas");
								knowledge_question_Dao.add(knowinfo);
								System.out.println("sssss");
								
								kk_string+=+",";
							}
							
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println(e.toString());
						}
						//sql_Save_Question.add("insert into tb_questioin")
					}
				}  catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(e.toString());
				}
				break;
			}
		}
	}
	
	@Override
	public Serializable uploadQuestion(HttpServletRequest request, String knowledges,
			String answer, String level) throws Exception {
		MsgInfo msg = new MsgInfo();
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
                    
                    if(!myFileName.contains("zip")&&!myFileName.contains("rar"))
                    {
                    	msg.setMsg(GlobalValues.CODE_UPLOAD_NOT_RIGHT_FORM
            					, GlobalValues.MSG_UPLOAD_NOT_RIGHT_FORM);
                    	return msg;
                    }
                    if(!"".equals(myFileName.trim())){  
                        System.out.println(myFileName);  
                        //重命名上传后的文件名  
                        String fileName = "demoUpload" + file.getOriginalFilename();  
                        //定义上传路径
                      //获取文件 存储位置 放在项目根目录
    	        		String realPath = request.getSession().getServletContext()
    	        				.getRealPath("/"+category);
    	        		
    	        		Date date =new Date();
    	        		String Save_Path=realPath+"/"+date.getTime()+"/";
    	        		File pathFile = new File(Save_Path);
    	        		if (!pathFile.exists()) {
    	        			//文件夹不存 创建文件
    	        			pathFile.mkdirs();
    	        		}
    	        		String path = pathFile.getAbsolutePath()+"/" + fileName;
                        File localFile = new File(path);  
                        file.transferTo(localFile);
                        if(!HandleZip(path, Save_Path))
                        {
                        	msg.setMsg(GlobalValues.CODE_UPLOAD_UNZIP_FAIL
                					, GlobalValues.MSG_UPLOAD_UNZIP_FAIL);
                        	return msg;
                        }
                        
                        System.out.println(path);  
                       
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
