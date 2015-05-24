package com.ccc.test.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ccc.test.service.interfaces.IFileService;
import com.ccc.test.service.interfaces.IKnowledgeService;
import com.ccc.test.utils.Bog;

//文件控制器 负责上传下载文件 测试阶段
@Controller
@RequestMapping("/file")
public class FileController {

	@Autowired
	IFileService fileService;
	
	@Autowired
	IKnowledgeService knowledgeService;
	
	@RequestMapping("/upload")
	public String upload(
			HttpServletRequest request,
			@RequestParam CommonsMultipartFile file,
			ModelMap model
			){
		Bog.print("asdasdsa");
		
		try {
			
			Serializable ret = knowledgeService.uploadQuestion(request);
			Bog.print((String)ret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		fileService.uploadFile(file, category, "", 0);
//	        if(file != null && !file.isEmpty()){  
//	            try {
//	    			String filename = file.getOriginalFilename();
//	    			System.out.println("fileName--->"+filename+"category="+category); 
//	    			if (category== null ){
//	    				category = "tmpfile";
//	    			}
//	            	//获取文件 存储位置 放在项目根目录
//	        		String realPath = request.getSession().getServletContext()
//	        				.getRealPath("/"+category);
//	        		File pathFile = new File(realPath);
//	        		if (!pathFile.exists()) {
//	        			//文件夹不存 创建文件
//	        			pathFile.mkdirs();
//	        		}
//	                FileOutputStream os = new FileOutputStream(
//	                		pathFile.getAbsolutePath()
//	                		+"/"+new Date().getTime()+file.getOriginalFilename());  
//	                InputStream in = file.getInputStream();  
//	                int b=0;  
//	                while((b=in.read())!=-1){  
//	                    os.write(b);  
//	                }  
//	                os.flush();  
//	                os.close();  
//	                in.close();  
//	            } catch (Exception e) {  
//	                e.printStackTrace();  
//	            }  
//	        }  
		return "main";
	}
	
	/**支持上传多个文件
	 * @param request
	 * @param category
	 * @param response
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/upload2")
	public String upload2(
			HttpServletRequest request,
			@RequestParam String category,
			HttpServletResponse response) throws IllegalStateException, IOException{
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
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
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){  
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
                    }  
                }  
                //记录上传该文件后的时间  
                int finaltime = (int) System.currentTimeMillis();  
                System.out.println(finaltime - pre);  
            }
        }
            return "";
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
  
//        //获取下载文件名字
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
