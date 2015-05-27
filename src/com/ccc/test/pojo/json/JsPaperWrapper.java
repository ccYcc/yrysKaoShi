package com.ccc.test.pojo.json;

import java.util.List;

@Deprecated
public class JsPaperWrapper {

	int paperId;
	
	List<JsPaperQuestWrapper> questions;
	
	class JsPaperQuestWrapper{
		
		int questNumInPaper;//问题的序号
		List<JsKnowledgeTreeNode> knowledgeNodes;
	}
}
