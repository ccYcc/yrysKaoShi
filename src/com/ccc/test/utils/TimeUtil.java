package com.ccc.test.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	public static String format(long ms,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(ms));
	}
}
