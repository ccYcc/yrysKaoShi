package com.ccc.test.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author ycc
 * 数学处理 如随机获取字段
 */
public class NumberUtil {

	public static <T> List<T> RandomGetSome(List<T> list,int size)
	{
		if(list.size()<=0)return list;
		if(size>=list.size())return list;
		if(size<=0)return null;
		boolean[]isused=new boolean[list.size()];
		
		List<T> res=new ArrayList<T>();
		
		for(int i=0;i<size;i++)
		{
			int index=-1;
			while(index==-1||isused[index])
				index=(int)(Math.random()*list.size());
			isused[index]=true;
			res.add(list.get(index));
		}
		return res;
	}
	
	public static String formatNumber(double number ,String patten){
		DecimalFormat format = new DecimalFormat(patten);
		return format.format(number);
		
	}
}
