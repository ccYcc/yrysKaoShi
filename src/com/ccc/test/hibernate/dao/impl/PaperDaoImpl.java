package com.ccc.test.hibernate.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.ccc.test.hibernate.AbSessionHelper;
import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.TeacherPaperInfo;
import com.ccc.test.pojo.UserInfo;
import com.ccc.test.utils.ListUtil;

/**
 * 持久化老师上传的paper信息
 *
 */
public class PaperDaoImpl implements IBaseHibernateDao<TeacherPaperInfo>{

	@Override
	public TeacherPaperInfo getById(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeacherPaperInfo> getList(Map<String, Object> args) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(TeacherPaperInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(List<TeacherPaperInfo> list) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(TeacherPaperInfo t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable add(final TeacherPaperInfo paper)  throws Exception{
		
		return new AbSessionHelper<Serializable>() {
			@Override
			public Serializable handleSession(Session s) {
				return s.save(paper);
			}
		}.getResult();
	}

	@Override
	public Serializable add(List<TeacherPaperInfo> ts) {
		// TODO Auto-generated method stub
		return null;
	}

}
