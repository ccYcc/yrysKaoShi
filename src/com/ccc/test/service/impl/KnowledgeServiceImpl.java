package com.ccc.test.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ccc.test.hibernate.dao.interfaces.IBaseHibernateDao;
import com.ccc.test.hibernate.dao.interfaces.IknowledgeDao;
import com.ccc.test.pojo.KnowledgeInfo;
import com.ccc.test.pojo.KnowledgeRelationInfo;
import com.ccc.test.pojo.MsgInfo;
import com.ccc.test.service.interfaces.IFileService;
import com.ccc.test.service.interfaces.IKnowledgeService;
import com.ccc.test.utils.Bog;
import com.ccc.test.utils.GlobalValues;
import com.ccc.test.utils.ListUtil;
import com.ccc.test.utils.UtilDao;

public class KnowledgeServiceImpl implements IKnowledgeService{

	@Autowired
	IknowledgeDao knowledgeDao;
	
//	@Autowired
//	IBaseHibernateDao<KnowledgeRelationInfo> knowledgeRelationDao;
	@Override
	public Serializable getKnowlegeByID(Integer id) throws Exception {
		// TODO Auto-generated method stub
		if(id!=null&&id<0)return null;
		List<KnowledgeInfo> knowledges = null;
		if(id==null)id=-1;
		knowledges=knowledgeDao.getChild(id);
		for(KnowledgeInfo info : knowledges)
		{
			List<KnowledgeInfo> knowledges_ch = knowledgeDao.getChild(info.getId());
			if(knowledges_ch.size()>0)
				info.setHasChildren(true);
			else
				info.setHasChildren(false);
		}
		return (Serializable)knowledges;
	}

	@Override
	public Serializable uploadKnowledge(HttpServletRequest request) throws Exception {
		MsgInfo msg = new MsgInfo();
		IFileService fileservice = new FileServiceImpl();
		String filePath = (String)fileservice.SaveUploadFile(request, IKnowledgeService.category);
		if(filePath==null||(!filePath.contains("csv")))
        {
        	msg.setMsg(GlobalValues.CODE_UPLOAD_NOT_RIGHT_FORM
					, GlobalValues.MSG_UPLOAD_NOT_RIGHT_FORM);
        	return msg;
        }
        else
        {
        	List<String> res = HandleKnowledges(filePath);
        	String ss="";
        	for(String t : res)
        		ss+=t+"\n";
        	return (Serializable) ss;
        }
	}
	private List<String> HandleKnowledges(String DataPath)
	{
		List<String>fail_list=new ArrayList<String>();
		FileInputStream finput = null;
		try {
			finput = new FileInputStream(new File(DataPath));
			List<String> filecontant = IOUtils.readLines(finput, "UTF-8");
//			String ss_temp = new String(filecontant.get(0).getBytes();
			int contant_size = ListUtil.OverridStringSplit(filecontant.get(0), ',').size();
			filecontant.remove(0);
			Map<String,KnowledgeInfo>check_map=new HashMap<String, KnowledgeInfo>();
			for(String contant : filecontant)
			{
				List<String> temp=ListUtil.OverridStringSplit(contant, ',');
				Bog.print(contant+"\t"+temp.size());
				if(temp.size()!=contant_size)continue;
//				for(String i  : temp)
//					Bog.print(i);
				if(temp.get(0).equals("")||temp.size()<2){
					fail_list.add(contant+" 格式错误");
					continue;
				}
				if(temp.get(IKnowledgeService.knowledge_index).equals(""))
				{
					fail_list.add(contant+" 知识点不能为空");
					continue;
				}
				KnowledgeInfo k_info=new KnowledgeInfo();
				k_info.setDescription(temp.get(IKnowledgeService.desc_index));
				k_info.setName(temp.get(IKnowledgeService.knowledge_index));
				if(temp.get(IKnowledgeService.parent_knowledge_index).equals(k_info.getName()))
				{
					fail_list.add(contant+" 不能以自己为父知识点");
					continue;
				}
				if(!temp.get(IKnowledgeService.parent_knowledge_index).equals(""))
					check_map.put(k_info.getName()+","+temp.get(IKnowledgeService.parent_knowledge_index), k_info);
				else
					check_map.put(k_info.getName()+","+"null", k_info);
			}
			Bog.print(check_map.size()+"");
			boolean isSaveData=true;
			Map.Entry<String, KnowledgeInfo>entry;
			while(isSaveData)
			{
				isSaveData=false;
				for(Iterator<Map.Entry<String, KnowledgeInfo>>it=check_map.entrySet().iterator();
				it.hasNext();)
				{
					entry=it.next();
					List<String> temp=ListUtil.OverridStringSplit(entry.getKey(), ',');
					Bog.print(entry.getKey()+"\t"+entry.getValue().getName());
					try {
						Serializable serializable=null;
						
						if(temp.get(1).equals("null"))
						{
							entry.getValue().setPid(-1);
							serializable = knowledgeDao.add(entry.getValue());
							
						}
						else
						{
							Map<String, Object> args=new HashMap<String, Object>();
							args.put("name", temp.get(1));
							List<KnowledgeInfo> par_know = knowledgeDao.getList(args);
							if(par_know.size()>0)
							{
								entry.getValue().setPid(par_know.get(0).getId());
								serializable = knowledgeDao.add(entry.getValue());
							}else
								continue;//父节点暂时不存在
						}
						if(serializable==null)
						{
							knowledgeDao.update(entry.getValue());
//							Map<String,Object>args=new HashMap<String,Object>();
//			        		args.put(KnowledgeRelationInfo.COLUMN_CHILDREN_IDS,entry.getValue().getId());
//			        		UtilDao.DeleteByArgs(new KnowledgeRelationInfo(), args);
						}
//						KnowledgeRelationInfo infos =new KnowledgeRelationInfo();
//						infos.setChildrenId(entry.getValue().getId());
//						infos.setParentId(entry.getValue().getPid());
//						knowledgeRelationDao.add(infos);
						isSaveData=true;
						it.remove();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Bog.print(e.toString());
					}
				}
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			Bog.print(e1.toString());
		}finally{
			if(finput!=null)
				try {
					finput.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return fail_list;
	}
}
