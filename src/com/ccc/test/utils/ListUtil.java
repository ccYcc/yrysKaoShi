package com.ccc.test.utils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ccc.test.pojo.UserAnswerLogInfo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ListUtil {

	public static boolean isEmpty(@SuppressWarnings("rawtypes") List list) {

		boolean ret= (list == null || list.isEmpty());
		return ret;
	}
	
	public static boolean isNotEmpty(@SuppressWarnings("rawtypes") List list){
		
		return !isEmpty(list);
	}

	public static String listToStringJoinBySplit(List list , String split){
		if ( isEmpty(list) )return "";
		StringBuffer sb = new StringBuffer();
		for ( Object o : list ){
			sb.append(o).append(split);
		}
		return sb.substring(0, sb.length()-1);
	}
	
	public static<T> List<T> jsonArrToList(String strs,TypeReference<List<T>> t){
		List<T> ret = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			ret = mapper.readValue(strs, t);
			return ret;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**将字符串strs用split割开，转为list
	 * @param strs
	 * @param split
	 * @return
	 */
	public static List<String> stringsToListSplitBy(String strs,String split){
		if ( strs == null )return null;
		String sp = null;
		if ( split != null ){
			sp = split;
		} else {
			sp = ",";
		}
		String[] s = strs.split(sp);
		if ( s == null )return null;
		return Arrays.asList(s);
	}
	
	public static List<Integer> stringsToTListSplitBy(String strs,String split){
		List<String> tmp =stringsToListSplitBy(strs, split);
		List<Integer> idsnum = null;
		if ( ListUtil.isNotEmpty(tmp) ){
			idsnum = new ArrayList<Integer>();
			for ( String idstr : tmp ){
				if(StringUtils.isNumeric(idstr))
				idsnum.add(Integer.valueOf(idstr));
			}
		}
		return idsnum;
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
	/**将字符串strs用split割开，转为list
	 * String 自带split存在问题，如 66,,切出来为["66"]，希望["66","",""]
	 * @param strs
	 * @param split
	 * @return
	 */
	public static List<String> OverridStringSplit(String strs,char... split){
		if ( strs == null )return null;
		char sp;
		if ( split != null && split.length > 0){
			sp = split[0];
		} else {
			sp = ',';
		}
		List<String> reslut=new ArrayList<String>();
		char[] ch = strs.toCharArray();
		String res="";
		for(int i=0;i<ch.length;i++)
		{
			if(ch[i]==sp)
			{
				reslut.add(res);
				res="";
			}
			else
				res+=ch[i];
		}
		reslut.add(res);
		return reslut;
	}
}
