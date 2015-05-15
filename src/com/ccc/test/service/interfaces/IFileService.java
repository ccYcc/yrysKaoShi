package com.ccc.test.service.interfaces;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

	/**上传文件接口
	 * @param file 要上传的文件
	 * @param category 文件类别 如questions（代表题目），knowledges（代表知识点） 
	 * @param targetPath 文件要保存的路径（包含文件名）
	 * @param uid 上传者id
	 * @return 成功返回文件id，否则返回错误信息
	 */
	Serializable uploadFile(	MultipartFile file,String category,String targetPath,int uid);
	
	Serializable downloadFile(String targetPath,int uid);
	
}
