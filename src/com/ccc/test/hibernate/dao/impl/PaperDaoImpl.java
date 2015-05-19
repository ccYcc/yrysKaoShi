package com.ccc.test.hibernate.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.ccc.test.hibernate.AbSessionHelper;
import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.PaperInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.utils.ListUtil;

/**
 * 持久化老师上传的paper信息
 *
 */
public class PaperDaoImpl implements IBaseHibernateDao<PaperInfo>{

	@Override
	public PaperInfo getById(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaperInfo> getList(Map<String, Object> args) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(PaperInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(List<PaperInfo> list) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(PaperInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable add(final PaperInfo paper)  throws Exception{
		
		return new AbSessionHelper<Serializable>() {
			@Override
			public Serializable handleSession(Session s) {
				return s.save(paper);
			}
		}.getResult();
	}

}
