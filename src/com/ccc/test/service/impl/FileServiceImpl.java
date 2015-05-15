package com.ccc.test.service.impl;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.ccc.test.service.interfaces.IFileService;

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

}
