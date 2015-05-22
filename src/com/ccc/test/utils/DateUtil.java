package com.ccc.test.utils;

import java.text.SimpleDateFormat;

/**
 * @author cxl
 * 将系统时间戳转换为格式化时间
 *
 */
public class DateUtil {

	public static String dateFormat(long createTime)
	{
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd   hh:mm:ss   ");
		String formatTime = fmt.format(createTime);
		return formatTime;
	}
	public static void main(String[] args)
	{
		long nowTime = System.currentTimeMillis();
		
		System.out.print(DateUtil.dateFormat(nowTime));
	}
}
