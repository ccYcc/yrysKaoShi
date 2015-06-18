package com.ccc.test.service.interfaces;

import java.io.Serializable;

import com.ccc.test.pojo.UserStatisticInfo;

public interface IUserStatisticService {

	/**添加活跃记录
	 * @param usi
	 * @return 返回添加后的对象id
	 */
	Serializable add(UserStatisticInfo usi)  throws Exception ;
	
	/**获取某天中每个小时的活跃用户数，
	 * @param datetime 某天的时间戳
	 * @return Map<hour,number>，hour=[0-24]， 如果没有数据默认number=0
	 */
	Serializable getHuoyueNumOfDay(long datetime) throws Exception;
	
	/**获取某个月中每天的活跃用户数
	 * @param monthtime 某月的时间戳
	 * @return Map<day,number>，day=一月中的天 （如：1日，2日。。。）， 如果没有数据默认number=0
	 */
	Serializable getHuoyueNumOfMonth(long monthtime)throws Exception;
	
	/**获取某个月中每天的注册用户数
	 * @param monthtime 某月的时间戳
	 * @return Map<day,number>，day=一月中的天（如：1日，2日。。。） ， 如果没有数据默认number=0
	 */
	Serializable getRegisterNumOfMonth(long monthtime)throws Exception;
	
	
}
