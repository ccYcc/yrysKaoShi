package com.ccc.test.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	public static String format(long ms,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(ms));
	}
	
	public static String secondsToReadableStr(long sec){
		if ( sec < 0 )return "";
		StringBuffer sb = new StringBuffer();
		if ( sec < 60 ){
			sb.append(sec).append("秒");
		} else {
			long newsec = sec % 60;
			long min = sec / 60;
			if ( min < 60 ){
				sb.append(min).append("分").append(newsec).append("秒");
			} else {
				long newmin = min % 60;
				long hour = min / 60;
				sb.append(hour).append("小时").append(newmin).append("分").append(newsec).append("秒");
			}
		}
		return sb.toString();
	}
}
