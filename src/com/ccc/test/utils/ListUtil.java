package com.ccc.test.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ListUtil {

	public static boolean isEmpty(@SuppressWarnings("rawtypes") List list) {

		boolean ret= (list == null || list.isEmpty());
		return ret;
	}
	
	public static boolean isNotEmpty(@SuppressWarnings("rawtypes") List list){
		
		return !isEmpty(list);
	}

	/**将字符串strs用split割开，转为list
	 * @param strs
	 * @param split
	 * @return
	 */
	public static List<String> stringsToListSplitBy(String strs,String... split){
		if ( strs == null )return null;
		String sp = null;
		if ( split != null && split.length > 0){
			sp = split[0];
		} else {
			sp = ",";
		}
		String[] s = strs.split(sp);
		if ( s == null )return null;
		return Arrays.asList(s);
	}
	public static boolean isEmpty( Object o) {
		
		if ( o instanceof Map ){
			Map map = (Map) o;
			return map.isEmpty();
		} else if ( o instanceof List ){
			List l = (List)o;
			return l.isEmpty();
		} else if ( o instanceof String ){
			String s = (String)o;
			if ( s == null || s.trim().equals(""))return true;
			return false;
		} else {
			return false;
		}
		
	}
	
	/**将一列字符转换为查询语句   in ('String','String')或者in (Integer,Integer)
	 * @param <T>
	 * @return
	 */
	public static <T> String ListTo_HQL_IN(List<T>value){
		if ( value == null || value.size()<=0)return "";
		String reslut = "in (";
		String split="";
		
		if(value.get(0) instanceof String)
			split="'";
		else if(value.get(0) instanceof Integer)
			split="";
		else return "";
		
		reslut+=split+value.get(0)+split;
		for(int i=1;i<value.size();i++)
		{
			reslut+=","+split+value.get(i)+split;
		}
		reslut+=")";
		
		return reslut;
	}
}
