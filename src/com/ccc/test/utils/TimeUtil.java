package com.ccc.test.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	public static String format(long ms,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(ms));
	}
	
	public static long getStartDayTime(long dayms) throws ParseException{
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
	    SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    Date start = formater2.parse(formater.format(new Date(dayms))+ " 00:00:00");
		return start.getTime();
	}
	
	public static long getEndDayTime(long dayms) throws ParseException{
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
	    SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    Date  end = formater2.parse(formater.format(new Date(dayms))+ " 23:59:59");
		return end.getTime();
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
