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
		//鍒涘缓涓€涓€氱敤鐨勫閮ㄥ垎瑙ｆ瀽鍣? 
        CommonsMultipartResolver multipartResolver 
        						= new CommonsMultipartResolver(request.getSession().getServletContext());  
        //鍒ゆ柇 request 鏄惁鏈夋枃浠朵笂浼?鍗冲閮ㄥ垎璇锋眰  
        if(multipartResolver.isMultipart(request)){  
            //杞崲鎴愬閮ㄥ垎request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //鍙栧緱request涓殑鎵€鏈夋枃浠跺悕  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //璁板綍涓婁紶杩囩▼璧峰鏃剁殑鏃堕棿锛岀敤鏉ヨ绠椾笂浼犳椂闂? 
                int pre = (int) System.currentTimeMillis();  
                //鍙栧緱涓婁紶鏂囦欢  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){
//                	questService.uploadQuestion(file, "", answer, level);
                    //鍙栧緱褰撳墠涓婁紶鏂囦欢鐨勬枃浠跺悕绉? 
                    String myFileName = file.getOriginalFilename();  
                    //濡傛灉鍚嶇О涓嶄负鈥溾€?璇存槑璇ユ枃浠跺瓨鍦紝鍚﹀垯璇存槑璇ユ枃浠朵笉瀛樺湪  
                    if(!"".equals(myFileName.trim())){  
                        System.out.println(myFileName);  
                        //閲嶅懡鍚嶄笂浼犲悗鐨勬枃浠跺悕  
                        String fileName = file.getOriginalFilename();  
                        //瀹氫箟涓婁紶璺緞
                      //鑾峰彇鏂囦欢 瀛樺偍浣嶇疆 鏀惧湪椤圭洰鏍圭洰褰?
    	        		String realPath = request.getSession().getServletContext()
    	        				.getRealPath("/"+category);
    	        		
    	        		Date date =new Date();
    	        		String Save_Path=realPath+"/";
    	        		File pathFile = new File(Save_Path);
    	        		if (!pathFile.exists()) {
    	        			//鏂囦欢澶逛笉瀛?鍒涘缓鏂囦欢
    	        			pathFile.mkdirs();
    	        		}
    	        		String path = pathFile.getAbsolutePath()+"/" + fileName;
                        File localFile = new File(path);  
                        file.transferTo(localFile);
                        savePath = path;
                    }
                    break;
                }  
                //璁板綍涓婁紶璇ユ枃浠跺悗鐨勬椂闂? 
                int finaltime = (int) System.currentTimeMillis();  
                System.out.println(finaltime - pre);  
            }
        }
		return savePath;
	}
	
	public  void unzip(String zipFile, String dest, String passwd) throws ZipException {    
        ZipFile zFile = new ZipFile(zipFile);  // 棣栧厛鍒涘缓ZipFile鎸囧悜纾佺洏涓婄殑.zip鏂囦欢    
        zFile.setFileNameCharset("GBK");       // 璁剧疆鏂囦欢鍚嶇紪鐮侊紝鍦℅BK绯荤粺涓渶瑕佽缃?   
        if (!zFile.isValidZipFile()) {   // 楠岃瘉.zip鏂囦欢鏄惁鍚堟硶锛屽寘鎷枃浠舵槸鍚﹀瓨鍦ㄣ€佹槸鍚︿负zip鏂囦欢銆佹槸鍚﹁鎹熷潖绛?   
        	File file=new File(zipFile);
            file.delete();
        	throw new ZipException("鍘嬬缉鏂囦欢涓嶅悎娉?鍙兘琚崯鍧?");
        }    
        File destDir = new File(dest);     // 瑙ｅ帇鐩綍    
        if (destDir.isDirectory() && !destDir.exists()) {    
            destDir.mkdir();    
        }    
        if (zFile.isEncrypted()) {    
            zFile.setPassword(passwd.toCharArray());  // 璁剧疆瀵嗙爜    
        }    
        zFile.extractAll(dest);      // 灏嗘枃浠舵娊鍑哄埌瑙ｅ帇鐩綍(瑙ｅ帇)
        File file=new File(zipFile);
        file.delete();
    }
}
