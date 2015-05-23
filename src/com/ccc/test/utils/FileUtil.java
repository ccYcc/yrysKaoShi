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
	
	public static Serializable saveFile( HttpSession session,MultipartFile file,String category){
		if(file != null && !file.isEmpty()){  
    			String filename = file.getOriginalFilename();
    			Bog.print("save fileName--->"+filename+" category="+category); 
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
        		String saveName = new Date().getTime()+"_"+filename;
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
}
