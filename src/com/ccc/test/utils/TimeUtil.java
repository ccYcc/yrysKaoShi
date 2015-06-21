package com.ccc.test.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {

	/**时间戳格式化方法
	 * @param ms 要格式化的时间戳
	 * @param format 格式化字符串 如 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String format(long ms,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(ms));
	}
	
	/**给定时间得到那天的最开始时间
	 * @param dayms
	 * @return
	 * @throws ParseException
	 */
	public static long getStartDayTime(long dayms) throws ParseException{
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
	    SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    Date start = formater2.parse(formater.format(new Date(dayms))+ " 00:00:00");
		return start.getTime();
	}
	
	/**给定时间得到那天的结束时间
	 * @param dayms
	 * @return
	 * @throws ParseException
	 */
	public static long getEndDayTime(long dayms) throws ParseException{
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
	    SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    Date  end = formater2.parse(formater.format(new Date(dayms))+ " 23:59:59");
		return end.getTime();
	}
	
	/**给定时间得到那个月的最开始时间
	 * @param dayms
	 * @return
	 * @throws ParseException
	 */
	public static long getStartDayTimeOfMonth(long dayms) throws ParseException{
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
	    SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
	    gcLast.setTimeInMillis(dayms);
	    gcLast.set(Calendar.DAY_OF_MONTH, 1);
	    Date start = formater2.parse(formater.format(gcLast.getTime())+ " 00:00:00");
		return start.getTime();
	}
	
	/**给定时间得到那个月的结束时间
	 * @param dayms
	 * @return
	 * @throws ParseException
	 */
	public static long getEndDayTimeOfMonth(long dayms) throws ParseException{
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
	    SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
	    gcLast.setTimeInMillis(dayms);
	    gcLast.add(Calendar.MONTH, 1);
	    gcLast.set(Calendar.DAY_OF_MONTH, 0);
	    Date  end = formater2.parse(formater.format(gcLast.getTime())+ " 23:59:59");
		return end.getTime();
	}
	
	/**给定时间得到时分秒格式的字符串
	 * @param sec
	 * @return
	 */
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
	/**将小时之后的数置为0 如 1900/12/12 12:12:12 的时间戳变为 1900/12/12 12:0:0 
	 * @param ms
	 * @return
	 */
	public static long zeroAfterHour(long ms){
		return ms / 1000 / 3600 * 1000 * 3600;
	}
	/**将日之后的数置为0 如 1900/12/12 12:12:12 的时间戳变为 1900/12/12 0:0:0 
	 * @param ms
	 * @return
	 * @throws ParseException
	 */
	public static long zeroAfterDay(long ms) throws ParseException{
		return getStartDayTime(ms);
	}
	public static boolean isInSameHour(long time1,long time2){
		return zeroAfterHour(time1) == zeroAfterHour(time2);
	}
	public static boolean isInSameDay(long time1,long time2){
		try {
			return zeroAfterDay(time1) == zeroAfterDay(time2);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static void main(String arg[]) throws ParseException{
		String timepatten = "yyyy/MM/dd HH:mm:ss";
		long now = System.currentTimeMillis();
		System.out.println(format(getStartDayTimeOfMonth(now),timepatten));
		System.out.println(format(getEndDayTimeOfMonth(now),timepatten));
		System.out.println(format(zeroAfterHour(now), timepatten));
		System.out.println(format(zeroAfterDay(now), timepatten));
	}
}
