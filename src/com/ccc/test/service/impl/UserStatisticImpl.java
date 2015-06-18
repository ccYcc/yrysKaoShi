package com.ccc.test.service.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ccc.test.hibernate.AbSessionHelper;
import com.ccc.test.pojo.UserStatisticInfo;
import com.ccc.test.service.interfaces.IUserStatisticService;
import com.ccc.test.utils.ListUtil;
import com.ccc.test.utils.TimeUtil;
import com.ccc.test.utils.UtilDao;

public class UserStatisticImpl implements IUserStatisticService{

	@Override
	public Serializable getHuoyueNumOfDay(long datetime) throws Exception {
		long startdaytime = TimeUtil.getStartDayTime(datetime);
		long enddaytime = TimeUtil.getEndDayTime(datetime);
		UserStatisticInfo t = new UserStatisticInfo();
		String sql = "from "+t.getClass().getSimpleName()+
				" where huoyuedate BETWEEN "+startdaytime+" AND "+enddaytime+"";
		List<UserStatisticInfo> list= UtilDao.getBySql(new UserStatisticInfo(), sql);
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		String formatpatten = "H时";
		for ( int i = 0 ; i < 24; i++ ){
			result.put(TimeUtil.format((i*3600*1000+startdaytime),formatpatten), 0);
		}
		if ( ListUtil.isNotEmpty(list) ){
			for ( UserStatisticInfo statistic : list ){
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
	public Serializable getHuoyueNumOfMonth(long monthtime) {
		return null;
	}

	@Override
	public Serializable getRegisterNumOfMonth(long monthtime) {
		return null;
	}

	@Override
	public Serializable add(final UserStatisticInfo usi) throws Exception {
		final long hourtime = usi.getHuoYueDate() / 1000 / 60 / 60 * 3600 * 1000;
		return new AbSessionHelper<Serializable>() {
			@Override
			public Serializable handleSession(Session s) {
				String sql = " from UserStatisticInfo WHERE " 
									+ UserStatisticInfo.COLUMN_HUOYUE_DATE +"=" + hourtime ;
				Query q = s.createQuery(sql);
				Object ret = q.uniqueResult();
				if ( ret instanceof UserStatisticInfo ){
					UserStatisticInfo info = (UserStatisticInfo) ret;
					info.setHuoYueNum(info.getHuoYueNum()+1);
					s.update(info);
					return info.getId();
				} else {
					usi.setHuoYueNum(1);
					usi.setHuoYueDate(hourtime);
					usi.setId(0);
					return s.save(usi);
				}
			}
		}.getResult();
	}

}
