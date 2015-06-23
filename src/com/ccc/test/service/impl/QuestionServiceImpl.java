package com.ccc.test.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.KnowledgeQuestionRelationInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.pojo.UserAnswerLogInfo;
import com.ccc.test.service.interfaces.IAlgorithmService;
import com.ccc.test.service.interfaces.IFileService;
import com.ccc.test.service.interfaces.IQuestionService;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;
import com.ccc.test.utils.NumberUtil;
import com.ccc.test.utils.PropertiesUtil;
import com.ccc.test.utils.StringUtil;
import com.ccc.test.utils.UtilDao;

import net.lingala.zip4j.exception.ZipException;  

public class QuestionServiceImpl implements IQuestionService{

	@Autowired
	IBaseHibernateDao<QuestionInfo> questDao;
	
	@Autowired
	IBaseHibernateDao<KnowledgeInfo> knowledgeDao;
	
	@Autowired
	IBaseHibernateDao<KnowledgeQuestionRelationInfo> knowledge_question_Dao;
	
	@Autowired
	IAlgorithmService algorithmService;

	private Serializable HandleZip(final String FileAddrs, final String temp_Out_addrs)
	{
		IFileService file = new FileServiceImpl();
		String message="";
		try {

			file.unzip(FileAddrs,temp_Out_addrs,null);
			List<String> fail_list = HandleQuestions(temp_Out_addrs);
			for(String fail : fail_list)
				message+=fail+"\n";
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}
	private List<String> HandleQuestions(String DataPath)
	{
		File file=new File(DataPath);
		Set<String> Image_Path=new HashSet<String>();
		List<String>fail_list=new ArrayList<String>();
		for(File f:file.listFiles())
		{
			if(f.getName().contains("png")||f.getName().contains("jpg"))
			{
//				System.out.println("212"+f.getName());
				Image_Path.add(f.getName());
			}
		}
		FileInputStream finput = null;
		for(File f:file.listFiles())
		{
			if(f.getName().contains("csv"))
			{
				try {
					finput = new FileInputStream(f);
					List<String> filecontant = IOUtils.readLines(finput, "UTF-8");
					int contant_size = ListUtil.OverridStringSplit(filecontant.get(0), ',').size();
					filecontant.remove(0);
					for(String contant : filecontant)
					{
						List<String> temp=ListUtil.OverridStringSplit(contant, ',');
						if(temp.size()!=contant_size)continue;
						boolean iscontinue=false;
						Map<String,Object> args_map=new HashMap<String,Object>();
						List<String> knowledge_list=new ArrayList<String>();
						int initsize = knowledge_list.size();
						for(int i=IQuestionService.knowledge_index;i<temp.size();i++)//check 知识点是否存在
						{
							if(temp.get(i).equals(""))continue;
							if(Image_Path.contains(temp.get(IQuestionService.image_name_index))==false){
								iscontinue=true;
								break;
							}
							knowledge_list.add(temp.get(i));
						}
						if(iscontinue||initsize==knowledge_list.size()){
							fail_list.add(contant+"\t错误");
							continue;
						}
						args_map.put(IQuestionService.ARG_KNOWLEDGES, knowledge_list);
						args_map.put(IQuestionService.ARG_image_name, temp.get(IQuestionService.image_name_index));
						args_map.put(IQuestionService.ARG_ANSWER, temp.get(IQuestionService.answer_index));
						args_map.put(IQuestionService.ARG_level, temp.get(IQuestionService.level_index));
						args_map.put(IQuestionService.ARG_Image_URL, "./"+IQuestionService.category+"/"+temp.get(IQuestionService.image_name_index));
						args_map.put(IQuestionService.ARG_options, temp.get(IQuestionService.options_index));
						args_map.put("flag", 0);
						String res=SaveAQuestionByUpLoad(args_map);
						if(res!=null)
							fail_list.add(contant+"\t"+res);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					Bog.print(e1.toString());
					return fail_list;
				}finally{
					if(finput!=null)
						try {
							finput.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				break;
			}
		}
//		for(String ss :fail_list)
//			System.out.println(ss);
		return fail_list;
	}
	@Override
	public String SaveAQuestionByUpLoad(Map<String,Object>args_map)
	{
		List<String> knowledge_list=(List<String>) args_map.get(IQuestionService.ARG_KNOWLEDGES);
		String answer=(String) args_map.get(IQuestionService.ARG_ANSWER);
		String level=(String) args_map.get(IQuestionService.ARG_level);
		String options=(String) args_map.get(IQuestionService.ARG_options);
		if(knowledge_list==null)
			return "知识点为空";
		Map<String,Integer>knowledge_id_map = new HashMap<String, Integer>();
		for(String knowledge_name : knowledge_list)//check 知识点是否存在
		{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("name", knowledge_name);
			try {
				List<KnowledgeInfo> knowlist=knowledgeDao.getList(map);
				if(knowlist==null||knowlist.size()<=0)
				{
					return "知识点："+knowledge_name+"不存在";
				}else
					knowledge_id_map.put(knowledge_name,knowlist.get(0).getId());
				
			} catch (Exception e) {
				Bog.print(e.toString()+"\n"+knowledgeDao.getClass().getName());
				return "知识点："+knowledge_name+"存储错误";
			}
		}
        QuestionInfo quest = new QuestionInfo();
        quest.setAnswer(answer);
        quest.setLevel(level);
        quest.setOptions(options);
        quest.setFlag((Integer)(args_map.get("flag")));
        quest.setQuestionUrl((String)(args_map.get(IQuestionService.ARG_Image_URL)));
        try {
			//用url查找重复，若重复，则覆盖QuestionInfo，删除关系KnowledgeQuestionRelationInfo中所有的依赖
        	Map<String, Object> map = new HashMap<String,Object>();
    		map.put("questionUrl", quest.getQuestionUrl());
    		List<QuestionInfo> list = questDao.getList(map);
    		if(list!=null&&list.size()>0)
    		{
    			quest.setId(list.get(0).getId());
    			Map<String,Object>args=new HashMap<String,Object>();
        		args.put(KnowledgeQuestionRelationInfo.COLUMN_QuestionID,quest.getId());
        		UtilDao.DeleteByArgs(new KnowledgeQuestionRelationInfo(), args);
    		}else
    			questDao.add(quest);
    		
			for(String knowledge_name : knowledge_list)
			{
				KnowledgeQuestionRelationInfo knowinfo=new KnowledgeQuestionRelationInfo();
				knowinfo.setKnoeledgeId(knowledge_id_map.get(knowledge_name));
				knowinfo.setQuestionId(quest.getId());
				knowledge_question_Dao.add(knowinfo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Bog.print(e.toString());
			return "知识点存储错误";
		}
		//sql_Save_Question.add("insert into tb_questioin")
		return null;
	}
	@Override
	public Serializable uploadQuestion(HttpServletRequest request, String knowledges,
			String answer, String level) throws Exception {
		
		MsgInfo msg = new MsgInfo();
		IFileService fileservice = new FileServiceImpl();
		String filePath = (String)fileservice.SaveUploadFile(request, IQuestionService.category);
		
		if(filePath==null||(!filePath.contains("zip")&&!filePath.contains("rar")))
        {
        	msg.setMsg(GlobalValues.CODE_UPLOAD_NOT_RIGHT_FORM
					, GlobalValues.MSG_UPLOAD_NOT_RIGHT_FORM);
        	return msg;
        }
        else
        {
        	String res = (String) HandleZip(filePath, filePath.substring(0, filePath.lastIndexOf("/")));
        	if(!StringUtil.isEmpty(res))
            {
            	msg.setMsg(GlobalValues.CODE_UPLOAD_UNZIP_FAIL
    					, res);
            	return msg;
            }
        }
		return null;
	}

	@Override
	public Serializable uploadPaperQuest(List<QuestionInfo> questionInfos) {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		if(questionInfos==null)
		{
			msg.setMsg(GlobalValues.CODE_EMPTY_ENTITY, GlobalValues.MSG_EMPTY_ENTITY);
			return msg;
		}
		if(questionInfos.size()==0)
		{
			msg.setMsg(GlobalValues.CODE_EMPTY_LIST, GlobalValues.MSG_EMPTY_LIST);
			return msg;
		}		
		try {
				questDao.add(questionInfos);
				msg.setMsg(GlobalValues.CODE_ADD_SUCCESS, GlobalValues.MSG_ADD_SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setMsg(GlobalValues.CODE_ADD_FAILED, GlobalValues.MSG_ADD_FAILED);
			return msg;
		}
		return msg;
	}

	@Override
	public Serializable uploadQuestKnowledge(List<QuestionInfo> questionInfos) {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		for(QuestionInfo questionInfo:questionInfos)
		{
			int questID = questionInfo.getId();
			List<KnowledgeInfo> knowledgeInfos = questionInfo.getKnowledges();
//			如果有对应的知识点则写入数据表，如果没有则跳过
			if(knowledgeInfos!=null&&knowledgeInfos.size()>0)
			for(KnowledgeInfo knowledgeInfo:knowledgeInfos)
			{
				int kid = knowledgeInfo.getId();
				KnowledgeQuestionRelationInfo kqr = new KnowledgeQuestionRelationInfo();
				kqr.setQuestionId(questID);
				kqr.setKnoeledgeId(kid);
				try {
					knowledge_question_Dao.add(kqr);
					msg.setMsg(GlobalValues.CODE_ADD_SUCCESS
							, GlobalValues.MSG_ADD_SUCCESS);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					msg.setMsg(GlobalValues.CODE_ADD_FAILED,GlobalValues.MSG_ADD_FAILED);
					return msg;
				}
			}	
		}
		return msg;
	}
	@Override
	public Serializable getQuestionsByRandom(Integer knowledges_id, String level,
			int size) throws Exception {
		// TODO Auto-generated method stub
		Bog.print("here");
		List<QuestionInfo>qinfo;
		String in_hql=GetSelectQuestions("="+knowledges_id,level);
		Bog.print(in_hql);
		qinfo=UtilDao.getBySql(new QuestionInfo(), in_hql);
		qinfo = NumberUtil.RandomGetSome(qinfo, size);
//		for(QuestionInfo infos : qinfo)
//			Bog.print(infos.getId()+"");
		return (Serializable) qinfo;
	}
	public static int qustionnum=5;
//	@Override
//	public Serializable getOneQuestionsByMethod(Integer knowledges, String level)
//			throws Exception {
//		// TODO Auto-generated method stub
//		qustionnum--;
//		if(qustionnum<0)
//		{
//			qustionnum=5;
//			return null;
//		}
//		Serializable s= getQuestionsByRandom(knowledges,  level, 1);
//		return s;
//	}
	@Override
	public Serializable getQuestionsByRandom(List<Integer> knowledges,
			String level, int size) throws Exception {
		// TODO Auto-generated method stub
		String in_sql = ListUtil.ListTo_HQL_IN(knowledges);
		Bog.print("here");
		List<QuestionInfo>qinfo;
		String in_hql=GetSelectQuestions(in_sql,level);
		Bog.print(in_hql);
		qinfo=UtilDao.getBySql(new QuestionInfo(), in_hql);
		qinfo = NumberUtil.RandomGetSome(qinfo, size);
//		for(QuestionInfo infos : qinfo)
//			Bog.print(infos.getId()+"");
		return (Serializable) qinfo;
	}
	private String GetSelectQuestions(String KnoeledgeIds,String level)
	{
		if(StringUtils.isBlank(KnoeledgeIds))return null;
		if(StringUtils.isBlank(level))
			return "from QuestionInfo q where q.id in (select k.questionId from KnowledgeQuestionRelationInfo k " +
			"where k.KnoeledgeId "+KnoeledgeIds+")";
		return "from QuestionInfo q where q.id in (select k.questionId from KnowledgeQuestionRelationInfo k " +
				"where k.KnoeledgeId "+KnoeledgeIds+") and q.level='"+level+"'";
	}
	@Override
	public Serializable getOneQuestionsByMethodGloble(List<Integer> knowledges,
			String level,List<UserAnswerLogInfo>AnswerLog,HttpSession session) throws Exception {
		if(ListUtil.isEmpty(knowledges))return null;
		String sessionKey = "";
		String sessionKey_knowledges="session_goodknowledges";
		for(Integer kno : knowledges)//
		{
			sessionKey+=kno+",";
		}
		//获取All_Qusetion和knowledges_good
		List<QuestionInfo> All_Qusetion = (List<QuestionInfo>) session.getAttribute(sessionKey);
		List<Integer> knowledges_good = (List<Integer>) session.getAttribute(sessionKey_knowledges);
		if(ListUtil.isEmpty(knowledges_good)&&!ListUtil.isEmpty(AnswerLog))return null;
		if(ListUtil.isEmpty(All_Qusetion)&&!ListUtil.isEmpty(AnswerLog))return null;
		if(ListUtil.isEmpty(All_Qusetion))//不存咋session则直接获取所有题目
		{
			All_Qusetion = (List<QuestionInfo>) getQuestionsByRandom(knowledges,level,999999999);
			if(!ListUtil.isEmpty(All_Qusetion))
			{
				for(QuestionInfo qinfo : All_Qusetion)
				{
					SetQuestionKnoledges(qinfo);
				}
			}
		}
		if(ListUtil.isEmpty(knowledges_good))
		{
			knowledges_good=knowledges;
		}
		if(ListUtil.isEmpty(All_Qusetion))return null;
		if(ListUtil.isEmpty(AnswerLog)||AnswerLog.size()<knowledges.size())//回答问题数不超过知识点数时，随机推荐一道
		{
			int index = (int)(Math.random()*All_Qusetion.size());
			if(index>=All_Qusetion.size())index = All_Qusetion.size()-1;
			QuestionInfo qinfo = All_Qusetion.get(index);
			All_Qusetion.remove(index);
			session.setAttribute(sessionKey, All_Qusetion);
			session.setAttribute(sessionKey_knowledges, knowledges_good);
			List<QuestionInfo> r = new ArrayList<QuestionInfo>();r.add(qinfo);
			return (Serializable) r;
		}
		String goodbad = (String) algorithmService.CheckUserGoodBadKnowledgesWithNumber(AnswerLog,knowledges_good,3,true);
		List<Integer>bad = ListUtil.stringsToTListSplitBy(ListUtil.OverridStringSplit(goodbad, ';').get(1), ",");
		List<Integer>good = ListUtil.stringsToTListSplitBy(ListUtil.OverridStringSplit(goodbad, ';').get(0), ",");
		List<QuestionInfo> recQuestions = new ArrayList<QuestionInfo>();
		knowledges_good.removeAll(bad);
		if(good.size()==knowledges_good.size())return null;
		double score=0;
		for(Iterator<QuestionInfo>it=All_Qusetion.iterator();it.hasNext();)
		{
			double size=0;
			int ii=0;
			QuestionInfo qinfo = it.next();
			boolean isremove=false;
			if(qinfo.getKnowledges()==null)continue;
			for(KnowledgeInfo kinfo : qinfo.getKnowledges())
			{
				if(bad.contains(kinfo.getId()))
				{
					isremove=true;
					break;
				}else if(!good.contains(kinfo.getId())&&knowledges_good.contains(kinfo.getId()))
				{
					ii++;
				}
			}
			if(isremove){
				it.remove();
			}else if(ii==0)it.remove();
			else
				{
					size=1.0/(double)(ii);
					if(score<size)
					{
						recQuestions.clear();
						recQuestions.add(qinfo);
						score=size;
					}else if(score==size)
						recQuestions.add(qinfo);
				}
		}
		if(ListUtil.isEmpty(recQuestions))return null;
		QuestionInfo reslut = recQuestions.get((int)(Math.random()*recQuestions.size())%recQuestions.size());
		All_Qusetion.remove(reslut);
		session.setAttribute(sessionKey, All_Qusetion);
		session.setAttribute(sessionKey_knowledges, knowledges_good);
		
		List<QuestionInfo> r = new ArrayList<QuestionInfo>();r.add(reslut);
		return (Serializable) r;
	}
	public void SetQuestionKnoledges(QuestionInfo qinfo)
	{
		if(qinfo==null)return;
		Map<String,Object>args = new HashMap<String, Object>();
		args.put(KnowledgeQuestionRelationInfo.COLUMN_QuestionID, qinfo.getId());
		try {
			List<KnowledgeQuestionRelationInfo> kqinfolist = knowledge_question_Dao.getList(args);
			if(!ListUtil.isEmpty(kqinfolist))
			{
				List<KnowledgeInfo> kinfo =new ArrayList<KnowledgeInfo>();
				for(KnowledgeQuestionRelationInfo kqinfo : kqinfolist)
				{
					KnowledgeInfo kkinfo =new KnowledgeInfo();
					kkinfo.setId(kqinfo.getKnoeledgeId());
					kinfo.add(kkinfo);
				}
				qinfo.setKnowledges(kinfo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Serializable getOneQuestionsByMethod(List<Integer> knowledges,
			String level,List<UserAnswerLogInfo>AnswerLog,HttpSession session) throws Exception {
		// TODO Auto-generated method stub
		
//		return getOneQuestionsByMethodGloble(knowledges,
//				level,AnswerLog,session);
		if(ListUtil.isEmpty(knowledges))return null;
		String sessionKey_knowledges="session_goodknowledges";//存储掌握知识点或者不能判断的知识点列表
		//获取All_Qusetion和knowledges_good
		List<QuestionInfo> All_Qusetion = new ArrayList<QuestionInfo>();//抽取的题目集合
		List<Integer> knowledges_good = (List<Integer>) session.getAttribute(sessionKey_knowledges);
		if(ListUtil.isEmpty(AnswerLog))
			knowledges_good=null;
		if(ListUtil.isEmpty(knowledges_good)&&!ListUtil.isEmpty(AnswerLog))return null;
		if(ListUtil.isEmpty(knowledges_good))
		{
			knowledges_good=knowledges;
		}
		//每次获取每个知识点多少道题，在配置文件中配置
		if(!StringUtils.isNumeric(PropertiesUtil.getString("GetQustionNumber")))return null;
		int qnumber = Integer.parseInt(PropertiesUtil.getString("GetQustionNumber"));
		for(Iterator<Integer>it=knowledges_good.iterator();it.hasNext();)
		{
			Integer kinfoid = it.next();
			List<Integer> knowledgesid = new ArrayList<Integer>();
			knowledgesid.add(kinfoid);
			List<QuestionInfo> qlist = (List<QuestionInfo>) getQuestionsByRandom(knowledgesid,level,qnumber);
			if(ListUtil.isEmpty(qlist))
				it.remove();
			else
				All_Qusetion.addAll(qlist);
		}
		
		if(ListUtil.isEmpty(All_Qusetion)&&!ListUtil.isEmpty(AnswerLog))return null;
		if(!ListUtil.isEmpty(All_Qusetion))
		{
			HashSet<Integer> hs =new HashSet<Integer>();
			if(!ListUtil.isEmpty(AnswerLog))//去重
			{
				for(UserAnswerLogInfo uainfo : AnswerLog)
					hs.add(uainfo.getQid());
			}
			for(Iterator<QuestionInfo>it=All_Qusetion.iterator();it.hasNext();)//获取每个问题相关的知识点列表
			{
				QuestionInfo qinfo = it.next();
				if(hs.contains(qinfo.getId()))
				{
					it.remove();
				}else
					SetQuestionKnoledges(qinfo);
			}
		}
		if(ListUtil.isEmpty(All_Qusetion))return null;
		if(ListUtil.isEmpty(AnswerLog)||AnswerLog.size()<knowledges.size())//回答问题数不超过知识点数时，随机推荐一道
		{
			int index = (int)(Math.random()*All_Qusetion.size())%All_Qusetion.size();
			QuestionInfo qinfo = All_Qusetion.get(index);
			List<QuestionInfo> r = new ArrayList<QuestionInfo>();r.add(qinfo);
			session.setAttribute(sessionKey_knowledges, knowledges_good);
			return (Serializable) r;
		}
		String goodbad = (String) algorithmService.CheckUserGoodBadKnowledgesWithNumber(AnswerLog,knowledges_good,3,true);
		List<Integer>bad = ListUtil.stringsToTListSplitBy(ListUtil.OverridStringSplit(goodbad, ';').get(1), ",");
		List<Integer>good = ListUtil.stringsToTListSplitBy(ListUtil.OverridStringSplit(goodbad, ';').get(0), ",");
		List<QuestionInfo> recQuestions = new ArrayList<QuestionInfo>();
		knowledges_good.removeAll(bad);
		if(good.size()==knowledges_good.size())return null;//若不存在不能判断的知识点，则结束
		double score=0;
		for(Iterator<QuestionInfo>it=All_Qusetion.iterator();it.hasNext();)
		{
			double size=0;
			int ii=0;
			QuestionInfo qinfo = it.next();
			boolean isremove=false;
			if(qinfo.getKnowledges()==null)continue;
			for(KnowledgeInfo kinfo : qinfo.getKnowledges())
			{
				if(bad.contains(kinfo.getId()))//若该题目包含未掌握知识点，则删除
				{
					isremove=true;
					break;
				}else if(!good.contains(kinfo.getId())&&knowledges_good.contains(kinfo.getId()))//一个题目包含的暂时不能判断的知识点，并且这些知识点是用户选择的
				{
					ii++;
				}
			}
			if(isremove){
				it.remove();
			}else if(ii==0)it.remove();//所有知识点已经被判断是否掌握
			else
			{
				size=1.0/(double)(ii);
				if(score<size)//只选择未确定知识点数量最小的问题集合，然后随机选取一个进行推荐
				{
					recQuestions.clear();
					recQuestions.add(qinfo);
					score=size;
				}else if(score==size)
					recQuestions.add(qinfo);
			}
		}
		if(ListUtil.isEmpty(recQuestions))return null;
		QuestionInfo reslut = recQuestions.get((int)(Math.random()*recQuestions.size())%recQuestions.size());
		session.setAttribute(sessionKey_knowledges, knowledges_good);
		List<QuestionInfo> r = new ArrayList<QuestionInfo>();r.add(reslut);
		return (Serializable) r;
	}
	@Override
	public Serializable GetQuestionFromAnswerLog(UserAnswerLogInfo loginfo) {
		// TODO Auto-generated method stub
		try {
			return questDao.getById(loginfo.getQid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Serializable getQuestionsByRandom(List<Integer> knowledges,
			String level, int size,String condition) throws Exception {
		// TODO Auto-generated method stub
		String in_sql = ListUtil.ListTo_HQL_IN(knowledges);
		List<QuestionInfo>qinfo_list;
		String in_hql=GetSelectQuestions(in_sql,level);
		qinfo_list=UtilDao.getBySql(new QuestionInfo(), in_hql);
		if(qinfo_list!=null)
		{
			List<QuestionInfo>qinfo_list2=new ArrayList<QuestionInfo>();
			for(QuestionInfo qinfo : qinfo_list)
			{
				if(qinfo.getFlag()==0)
				{
					qinfo_list2.add(qinfo);
				}
			}
			qinfo_list = NumberUtil.RandomGetSome(qinfo_list2, size);
		}
		return (Serializable) qinfo_list;
	}
}
