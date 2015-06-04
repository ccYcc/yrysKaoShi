package com.ccc.test.utils;

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
	
	/**
	 * 
	 * @param n
	 * @param m
	 * @return 计算组合数，n选m,结果为小数，只获取近似值
	 */
	public static double GetCombination(Integer n,Integer m)
	{
		if(m==n)return 1.0;
		if(m>n)return 0;
		if(m<=0)return 1.0;
		if(n<1)return 0;
		int i=n;
		double reslut=1.0;
		for(int j=m;i>0&&j>0;i--,j--)
			reslut*=((double)i/(double)j);
		for(int j=n-m;j>0&&i>0;j--,i--)
			reslut*=((double)i/(double)j);
		return reslut;
	}
	public static void main(String[] args) {
		System.out.println(GetCombination(4,1));
		System.out.println(GetCombination(5,2));
		System.out.println(GetCombination(6,2));
	}
	
}
