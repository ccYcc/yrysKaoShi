package com.ccc.test.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

//文件控制器 负责上传下载文件 测试阶段
@Controller
@RequestMapping("/file")
public class FileController {

	/**
	 * @return
	 */
	@RequestMapping("/upload")
	public String upload(
			HttpServletRequest request,
			@RequestParam CommonsMultipartFile file,
			@RequestParam String category,
			ModelMap model
			){

	        if(file != null && !file.isEmpty()){  
	            try {
	    			String filename = file.getOriginalFilename();
	    			System.out.println("fileName--->"+filename+"category="+category); 
	    			if (category== null ){
	    				category = "tmpfile";
	    			}
	            	//获取文件 存储位置
	        		String realPath = request.getSession().getServletContext()
	        				.getRealPath("/"+category);
	        		File pathFile = new File(realPath);
	        		if (!pathFile.exists()) {
	        			//文件夹不存 创建文件
	        			pathFile.mkdirs();
	        		}
	                FileOutputStream os = new FileOutputStream(
	                		pathFile.getAbsolutePath()
	                		+"/"+new Date().getTime()+file.getOriginalFilename());  
	                InputStream in = file.getInputStream();  
	                int b=0;  
	                while((b=in.read())!=-1){  
	                    os.write(b);  
	                }  
	                os.flush();  
	                os.close();  
	                in.close();  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
		return "main";
	}
	
	/**
	 * @return
	 */
	@RequestMapping("/download")
	public String download(HttpServletRequest request,  
            HttpServletResponse response){
	  	String storeName="房地.txt";
	    String contentType = "application/octet-stream";  
		try {
			download(request, response, storeName, contentType);
		} catch (Exception e) {
			e.printStackTrace();
		}  
	  return null;
	}
	
	//文件下载 主要方法
    public static void download(HttpServletRequest request,  
            HttpServletResponse response, String downLoadPath, String contentType
           ) throws Exception {  
    	
        request.setCharacterEncoding("UTF-8");  
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
  
//        //获取项目根目录
//        String ctxPath = request.getSession().getServletContext()  
//                .getRealPath("");  
//        
//        //获取下载文件露肩
//        String downLoadPath = ctxPath+"/uploadFile/"+ storeName;  
        String filename = downLoadPath.substring(downLoadPath.lastIndexOf("/"));
        
        //获取文件的长度
        long fileLength = new File(downLoadPath).length();  

        //设置文件输出类型
        response.setContentType(contentType);  
        response.setHeader("Content-disposition", "attachment; filename="  
                + new String(filename.getBytes("utf-8"), "ISO8859-1")); 
        //设置输出长度
        response.setHeader("Content-Length", String.valueOf(fileLength));  
        //获取输入流
        bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
        //输出流
        bos = new BufferedOutputStream(response.getOutputStream());  
        byte[] buff = new byte[2048];  
        int bytesRead;  
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
            bos.write(buff, 0, bytesRead);  
        }  
        //关闭流
        bis.close();  
        bos.close();  
    }  
}
