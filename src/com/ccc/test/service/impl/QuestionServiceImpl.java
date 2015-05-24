package com.ccc.test.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.hibernate.dao.interfaces.IQuestionDao;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.KnowledgeQuestionRelationInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.pojo.QuestionInfo;
import com.ccc.test.service.interfaces.IFileService;
import com.ccc.test.service.interfaces.IQuestionService;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;
import com.ccc.test.utils.NumberUtil;
import com.ccc.test.utils.UtilDao;
import com.opensymphony.oscache.util.StringUtil;

import net.lingala.zip4j.exception.ZipException;  

public class QuestionServiceImpl implements IQuestionService{

	@Autowired
	IBaseHibernateDao<QuestionInfo> questDao;
	
	@Autowired
	IBaseHibernateDao<KnowledgeInfo> knowledgeDao;
	
	@Autowired
	IBaseHibernateDao<KnowledgeQuestionRelationInfo> knowledge_question_Dao;
	

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
				System.out.println("212"+f.getName());
				Image_Path.add(f.getName());
			}
		}
		for(File f:file.listFiles())
		{
			if(f.getName().contains("csv"))
			{
				try {
					List<String> filecontant = FileUtils.readLines(f);
					filecontant.remove(0);
					for(String contant : filecontant)
					{
						List<String> temp=ListUtil.OverridStringSplit(contant, ',');
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
						args_map.put(IQuestionService.ARG_Image_URL, "/"+IQuestionService.category+"/"+(new File(f.getParent())).getName()+"/"+temp.get(IQuestionService.image_name_index));
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
				}
				break;
			}
		}
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
			Serializable res = questDao.add(quest);
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
        	if(res!=null)
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
		try {
				questDao.add(questionInfos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Serializable uploadQuestKnowledge(List<QuestionInfo> questionInfos) throws Exception {
		// TODO Auto-generated method stub
		MsgInfo msg = new MsgInfo();
		for(QuestionInfo questionInfo:questionInfos)
		{
			int questID = questionInfo.getId();
			List<KnowledgeInfo> knowledgeInfos = questionInfo.getKnowledges();
			for(KnowledgeInfo knowledgeInfo:knowledgeInfos)
			{
				int kid = knowledgeInfo.getId();
				KnowledgeQuestionRelationInfo kqr = new KnowledgeQuestionRelationInfo();
				kqr.setQuestionId(questID);
				kqr.setKnoeledgeId(kid);
				Serializable ret = knowledge_question_Dao.add(kqr);
				Integer uid = (Integer) ret;
				if( uid == -1 ){
					msg.setMsg(GlobalValues.CODE_USERNAME_USED
							, GlobalValues.MSG_USERNAME_USED);
				} else if ( uid > 0 ){
					msg.setMsg(GlobalValues.CODE_SUCCESS

							, GlobalValues.MSG_SUCCESS);
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
		for(QuestionInfo infos : qinfo)
			Bog.print(infos.getId()+"");
		return (Serializable) qinfo;
	}
	public static int qustionnum=5;
	@Override
	public Serializable getOneQuestionsByMethod(Integer knowledges, String level)
			throws Exception {
		// TODO Auto-generated method stub
		qustionnum--;
		if(qustionnum<0)
		{
			qustionnum=5;
			return null;
		}
		Serializable s= getQuestionsByRandom(knowledges,  level, 1);
		return s;
	}
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
		for(QuestionInfo infos : qinfo)
			Bog.print(infos.getId()+"");
		return (Serializable) qinfo;
	}
	private String GetSelectQuestions(String KnoeledgeIds,String level)
	{
		return "from QuestionInfo q where q.id in (select k.questionId from KnowledgeQuestionRelationInfo k " +
				"where k.KnoeledgeId "+KnoeledgeIds+") and q.level='"+level+"'";
	}
	
	@Override
	public Serializable getOneQuestionsByMethod(List<Integer> knowledges,
			String level) throws Exception {
		// TODO Auto-generated method stub
		qustionnum--;
		if(qustionnum<0)
		{
			qustionnum=5;
			return null;
		}
		Serializable s= getQuestionsByRandom(knowledges,  level, 1);
		return s;
	}
}
