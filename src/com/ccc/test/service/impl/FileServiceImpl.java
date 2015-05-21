package com.ccc.test.service.impl;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.service.interfaces.IFileService;
import com.ccc.test.utils.GlobalValues;

public class FileServiceImpl implements IFileService{

	@Override
	public Serializable uploadFile(MultipartFile file, String category,
			String targetPath, int uid) {
		return null;
	}

	@Override
	public Serializable downloadFile(String targetPath, int uid) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Serializable SaveUploadFile(HttpServletRequest request,String category) throws Exception {
		
		String savePath=null;
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
                        String fileName = file.getOriginalFilename();  
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
                        System.out.println(path);
                        savePath = path;
                    }
                    break;
                }  
                //记录上传该文件后的时间  
                int finaltime = (int) System.currentTimeMillis();  
                System.out.println(finaltime - pre);  
            }
        }
		return savePath;
	}
	
	public  void unzip(String zipFile, String dest, String passwd) throws ZipException {    
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
}
