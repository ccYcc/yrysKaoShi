<%@page import="com.ccc.test.pojo.UserInfo"%>
<%@page import="com.ccc.test.utils.GlobalValues"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>"/>
    
    <title>上传试卷</title>
    
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<link rel="stylesheet" href="./js/themes/default/style.css" />
	<link rel="stylesheet" type="text/css" href="./css/globe.css"/>
		<link rel="stylesheet" type="text/css" href="./css/upload-paper.css"/>
	<link href="./css/template-style.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css"/>
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	<script src="./js/jstree.min.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
  			
  			var curClickBtnIndex = -1;
  			
  			var paper = {};
  			var questions = [];
  			paper.questions = questions;
  			var question = {};
  			question.questNumInPaper = 0;
  			question.knowledgeNodes = [];
  			
  			var selectIds = [],selectTexts = [];
  			function showTreeSelectedOfQuest(){
  				var ids = "#had_selected"+curClickBtnIndex;
  				$(ids).html('已选择: ' + selectTexts.join(', '));
  				hideKnowledgeTree();
<%-- 	  		  	$('#selected_ids').val(selectIds.join(','));--%>
  			}
  			function onTreeChanged(e, data){
	  		    var i, j;
	  		 	selectTexts = [];
		    	selectIds = [];
	  		    for(i = 0, j = data.selected.length; i < j; i++) {
	  		    	var node = data.selected[i];
	  		    	
	  		    	selectTexts.push(data.instance.get_node(node).text);
	  		      	selectIds.push(data.instance.get_node(node).id);
	  		    }
  			}
  			
  			function showKnowledgeTree(index){
  				curClickBtnIndex = index;
  				$("#dialog_mask").show();
  				$("#dialog").dialog("open");
  				
  			}
  			function hideKnowledgeTree(){
  				curClickBtnIndex = -1;
  				$("#dialog_mask").hide();
  				$("#dialog").dialog("close");
  				$("#knowledge_tree").jstree().deselect_all(true);
  			}
  			$("#knowledge_tree")
		  		// listen for event
	  		 .on('changed.jstree', function (e, data) {
	  			onTreeChanged(e,data);
	  		  }).jstree({
			  	  "checkbox" : { three_state : true },
				  "core" : {
				    "multiple" : true,
				  	"themes" : {"icons" : false},
					"data" :{
						'url' :'json/getKnowledges/?lazy',
					  	'dataType' : 'json',
					    'data' : function (node) {
					    	var nid = node.id;
					    	if ( nid === '#'){
					    		nid = -1;
					    	}
					      return { 'id' : nid};//要传递的参数
					    }
					}
				  },
			"plugins" : [ "wholerow", "checkbox" ],
				});
  			
			$("#add_quest").on({'click':function(){
  				var ulnode = $("#list");
  				var childnum = ulnode.children().length;
  				questnum = childnum+1;
  				var childnode = "<li><span>题号："+questnum+"、</span><input class='choose_knowledge_btn' type='button' value='选择知识点' id='"+questnum+"'/><span id='had_selected"+questnum+"'></span></li>";
  				ulnode.append(childnode);
  				$( "#list li input[type='button']:last" )
  						.on({'click':function(){
								showKnowledgeTree($(this).attr('id'));
						}});
  			}});
  			
  			$("#upload").button();
  			$("#dialog").dialog({
  				minWidth : 666,
  				maxHeigth:560,
  				buttons: {
  			        "确定": showTreeSelectedOfQuest,
  			        Cancel: function() {
  			        	hideKnowledgeTree();
  			        }
  			     },
  				close:function(){
  					hideKnowledgeTree();
  				}
  			});
  			hideKnowledgeTree();
	  	});
  	</script>
  </head>
  
  <body>
		<div class="btm_border">
		<div class="h_bg">
		<div class="wrap">
		<div class="header">
			<div class="logo">
				<h1><a href="javascript:void(0)"><img class="logo_img" src="img/logo1.png" alt=""/></a></h1>
			</div>
			<div class="user-icon">
				<a href="javascript:void(0)">
					<img class="head_user_img" id="photo" alt="" src="${sessionScope.session_user.headUrl}" width="48px" height="48px"/>
					${sessionScope.session_user.username}
				</a>
				<a href="user/loginOut.do">登出</a>
			</div>	
			<div class="clear"></div>
		</div>
		</div>
		</div>
		</div>
		<div class="content">
			<form action="teacher/uploadPaper"  id="form" method="post" enctype="multipart/form-data">
				<div class="choose_paper">
					<div id="paper_input">
						<input type="file" value="选择试卷文件" name="file"/>						
				  	</div>
				</div>
				<div class="submit_layer">
					<p id="input_tip">&nbsp;</p>
					<input type="submit" id="upload" value="开始上传"/>
				</div>
				<div class="clear"></div>
				<div class="separate_line"></div>
				<div class="paper_content_input">
					<div class="tip_text">
						为试卷题目标注知识点：
					</div>
					<div id="question_list">
						<ul id="list">
						</ul>
						<input type="button" id="add_quest" value="加一道题"/>
					</div>
				</div>
			</form>
		</div>
		<div id="dialog_mask" >
			<div id="dialog" title="勾选知识点">
				<div id="knowledge_tree"></div>
			</div>
		</div>
  </body>
</html>
