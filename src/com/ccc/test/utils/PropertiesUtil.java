package com.ccc.test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class PropertiesUtil {

	static Properties yrysProperties = new Properties();
	static String PROPERTIES_PATH = "/config/yrys.properties";
	
	public static String getString(String key){
		InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH);
		try {
			yrysProperties.load(is);
			String str =  yrysProperties.getProperty(key);
			return str;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}
		return null;
	}
	
	public static List<String> getStrings(String... keys){
		if ( keys == null || keys.length == 0)return null;
		InputStream is = yrysProperties.getClass().getResourceAsStream(PROPERTIES_PATH);
		try {
			List<String> ret = new ArrayList<String>();
			yrysProperties.load(is);
			for ( String s : keys ){
				String str =  yrysProperties.getProperty(s);
				ret.add(str);
			}
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}
		return null;
	}
	
}
