package com.ccc.test.service.interfaces;

import java.util.List;

public interface IDeleteService{

	void DeleteAllUserDatas();
	void DeleteAllQuestions();
	void DeleteAllKnowledages();
	<T> List<T> FindPojoBy_Like_Names(T t,String likeName);
	<T> void DeleteById(T t,Integer id);
}
