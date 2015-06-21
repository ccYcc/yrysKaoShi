package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ccc.test.hibernate.AbSessionHelper;
import com.ccc.test.pojo.HuoyueDayInfo;
import com.ccc.test.pojo.HuoyueMonthInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.service.interfaces.IUserStatisticService;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.ListUtil;
import com.ccc.test.utils.TimeUtil;
import com.ccc.test.utils.UtilDao;

public class UserStatisticImpl implements IUserStatisticService{

	@Override
	public Serializable getHuoyueNumOfDay(long datetime) throws Exception {
		long startdaytime = TimeUtil.getStartDayTime(datetime);
		long enddaytime = TimeUtil.getEndDayTime(datetime);
		Bog.print("getHuoyueNumOfDay = startdaytime " + TimeUtil.format(startdaytime, "yyyy/MM/dd   HH:mm:ss"));
		Bog.print("getHuoyueNumOfDay = enddaytime " + TimeUtil.format(enddaytime, "yyyy/MM/dd HH:mm:ss"));
		String sql = "from "+HuoyueDayInfo.class.getSimpleName()+
				" where huoyuedate BETWEEN "+startdaytime+" AND "+enddaytime+"";
		List<HuoyueDayInfo> list= UtilDao.getBySql(new HuoyueDayInfo(), sql);
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		String formatpatten = "dd日H时";
		for ( int i = 0 ; i < 24; i++ ){
			result.put(TimeUtil.format((i*3600*1000+startdaytime),formatpatten), 0);
		}
		if ( ListUtil.isNotEmpty(list) ){
			for ( HuoyueDayInfo statistic : list ){
				long huoyuetime = statistic.getHuoYueDate();
				String timekey = TimeUtil.format(huoyuetime, formatpatten);
				Integer tmpnum = result.get(timekey);
				if ( tmpnum == null ){
					result.put(timekey, statistic.getHuoYueNum());
				} else {
					result.put(timekey, statistic.getHuoYueNum()+tmpnum);
				}
			}
		}
		return (Serializable) result;
	}

	@Override
	public Serializable getHuoyueNumOfMonth(long monthtime) throws Exception {
		long startdaytime = TimeUtil.getStartDayTimeOfMonth(monthtime);
		long enddaytime = TimeUtil.getEndDayTimeOfMonth(monthtime);
		Bog.print("getHuoyueNumOfMonth = startdaytime " + TimeUtil.format(startdaytime, "yyyy/MM/dd   HH:mm:ss"));
		Bog.print("getHuoyueNumOfMonth = enddaytime " + TimeUtil.format(enddaytime, "yyyy/MM/dd HH:mm:ss"));
		HuoyueMonthInfo t = new HuoyueMonthInfo();
		String sql = "from HuoyueMonthInfo "+
				" where huoyuedate BETWEEN "+startdaytime+" AND "+enddaytime+"";
		List<HuoyueMonthInfo> list= UtilDao.getBySql(t, sql);
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		String formatpatten = "MM月dd日";
		for ( int i = 0 ; i < 31; i++ ){
			long newdaytime = i*3600*1000*24L+startdaytime;
			if ( newdaytime <= enddaytime )
			result.put(TimeUtil.format((newdaytime),formatpatten), 0);
		}
		if ( ListUtil.isNotEmpty(list) ){
			for ( HuoyueMonthInfo statistic : list ){
				long huoyuetime = statistic.getHuoYueDate();
				String timekey = TimeUtil.format(huoyuetime, formatpatten);
				Integer tmpnum = result.get(timekey);
				if ( tmpnum == null ){
					result.put(timekey, statistic.getHuoYueNum());
				} else {
					result.put(timekey, statistic.getHuoYueNum()+tmpnum);
				}
			}
		}
		return (Serializable) result;
	}

	@Override
	public Serializable getRegisterNumOfMonth(long monthtime) throws Exception {
		long startdaytime = TimeUtil.getStartDayTimeOfMonth(monthtime);
		long enddaytime = TimeUtil.getEndDayTimeOfMonth(monthtime);
		Bog.print("getRegisterNumOfMonth = startdaytime " + TimeUtil.format(startdaytime, "yyyy/MM/dd   HH:mm:ss"));
		Bog.print("getRegisterNumOfMonth = enddaytime " + TimeUtil.format(enddaytime, "yyyy/MM/dd HH:mm:ss"));
		String sql = "from " + UserInfo.class.getSimpleName()+
				" where "+UserInfo.COLUMN_CREATETIME+" BETWEEN "+startdaytime+" AND "+enddaytime+"";
		List<UserInfo> list= UtilDao.getBySql(new UserInfo(), sql);
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		String formatpatten = "MM月dd日";
		for ( int i = 0 ; i < 31; i++ ){
			long newdaytime = i*3600*1000*24L+startdaytime;
			if ( newdaytime <= enddaytime )
			result.put(TimeUtil.format((newdaytime),formatpatten), 0);
		}
		if ( ListUtil.isNotEmpty(list) ){
			for ( UserInfo statistic : list ){
				long createtime = statistic.getCreateTime();
				String timekey = TimeUtil.format(createtime, formatpatten);
				Integer tmpnum = result.get(timekey);
				if ( tmpnum == null ){
					result.put(timekey, 1);
				} else {
					result.put(timekey, 1+tmpnum);
				}
			}
		}
		return (Serializable) result;
	}

	@Override
	public Serializable addHuoyueDayOfUser(final UserInfo user) throws Exception {
		final long userlasttime = user.getLastLoginTime();
		final long nowtime = System.currentTimeMillis();
		if ( !TimeUtil.isInSameHour(userlasttime, nowtime) ){
			final long hourtime = TimeUtil.zeroAfterHour(nowtime);
			return new AbSessionHelper<Serializable>() {
				@Override
				public Serializable handleSession(Session s) {
					String sql = " from "+HuoyueDayInfo.class.getSimpleName()+" WHERE " 
										+ HuoyueDayInfo.COLUMN_HUOYUE_DATE +"=" + hourtime ;
					Query q = s.createQuery(sql);
					Object ret = q.uniqueResult();
					if ( ret instanceof HuoyueDayInfo ){
						HuoyueDayInfo info = (HuoyueDayInfo) ret;
						info.setHuoYueNum(info.getHuoYueNum()+1);
						s.update(info);
						return info.getId();
					} else {
						HuoyueDayInfo udi = new HuoyueDayInfo();
						udi.setHuoYueNum(1);
						udi.setHuoYueDate(hourtime);
						udi.setId(0);
						return s.save(udi);
					}
				}
			}.getResult();
		} else {
			return -1;
		}
	}

	@Override
	public Serializable addHuoyueMonthOfUser(UserInfo user) throws Exception {
		final long userlasttime = user.getLastLoginTime();
		final long nowtime = System.currentTimeMillis();
		final long daytime = TimeUtil.zeroAfterDay(nowtime);
		if ( !TimeUtil.isInSameDay(userlasttime, nowtime) ){//如果不是在同一天活跃则加入
			
			return new AbSessionHelper<Serializable>() {
				@Override
				public Serializable handleSession(Session s) {
					
					String sql = " from "+HuoyueMonthInfo.class.getSimpleName()+" WHERE " 
										+ HuoyueDayInfo.COLUMN_HUOYUE_DATE +"=" + daytime ;
					Query q = s.createQuery(sql);
					Object ret = q.uniqueResult();
					if ( ret instanceof HuoyueDayInfo ){
						HuoyueDayInfo info = (HuoyueDayInfo) ret;
						info.setHuoYueNum(info.getHuoYueNum()+1);
						s.update(info);
						return info.getId();
					} else {
						HuoyueMonthInfo udi = new HuoyueMonthInfo();
						udi.setHuoYueNum(1);
						udi.setHuoYueDate(daytime);
						udi.setId(0);
						return s.save(udi);
					}
				}
			}.getResult();
		} else {
			return -1;
		}
	}

}
