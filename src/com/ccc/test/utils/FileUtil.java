package com.ccc.test.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	public static String CATEGORY_PHOTO = "photos";
	public static String CATEGORY_PAPERS = "papers";
	public static String CATEGORY_QUESTIONS = "questions";
	
	/**
	 * @param session 请求的回话对象
	 * @param file 表单提交的文件
	 * @param category 文件所属类型 CATEGORY_PHOTO|CATEGORY_PAPERS|CATEGORY_QUESTIONS
	 * @param filename 保存的文件名（不包括后缀），如果为null那么使用：时间戳+""_+上传时的原文件名。
	 * @return
	 */
	public static Serializable saveFile( HttpSession session,MultipartFile file,String category,String filename){
		if(file != null && !file.isEmpty()){  
				
    			String oFilename = file.getOriginalFilename();
    			Bog.print("save fileName--->"+oFilename+" category="+category); 
    			if (category== null ){
    				category = "tmpfiles";
    			}
    			
    			String relative = "/resources/"+category+"/";
            	//获取文件 存储位置 放在项目根目录
        		String realPath = session.getServletContext()
        				.getRealPath(relative);
        		File pathFile = new File(realPath);
        		if (!pathFile.exists()) {
        			//文件夹不存 创建文件
        			pathFile.mkdirs();
        		}
        		String saveName = new Date().getTime()+"_"+oFilename;
        		if ( !StringUtil.isEmpty(filename) ){
        			if ( filename.contains(".") ){
        				saveName = filename.substring(0,filename.lastIndexOf(getSubffix(filename)))
            					+getSubffix(oFilename);
        			} else {
        				saveName = filename + getSubffix(oFilename);
        			}
        			
    			}
        		String savePath = pathFile.getAbsolutePath()+"/"+saveName;
                try {
                    FileOutputStream output = new FileOutputStream(savePath);  
                    InputStream input = file.getInputStream();
					IOUtils.copy(input, output);
	                IOUtils.closeQuietly(input);
	                IOUtils.closeQuietly(output);
	                return "."+relative+saveName;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
        }
		return null;
	}
	
	public static String getSubffix(String name){
		if ( StringUtil.isEmpty(name) || !name.contains(".") )return "";
		return name.substring(name.lastIndexOf("."));
	}
	
	public static String getNameByPath (String path) {
		if ( StringUtil.isEmpty(path)  )return path;
		path = path.replace("\\", "/");
		return path.substring(path.lastIndexOf("/")+1);
	}
}
