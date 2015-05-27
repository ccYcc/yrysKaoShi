<%@page import="com.ccc.test.utils.ListUtil"%>
<%@page import="com.ccc.test.pojo.GroupInfo"%>
<%@page import="com.ccc.test.pojo.UserInfo"%>
<%@page import="com.ccc.test.utils.GlobalValues"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<% 
	List<GroupInfo> clazzs = (List<GroupInfo>)request.getAttribute("groups");

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
  	<script type="text/javascript" src="./js/render.js"></script>
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	<script src="./js/jstree.min.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
  			
  			var curClickBtnIndex = -1;
  			var curMaxIndex = 0;
  			
  			var selectIds = [],selectTexts = [];
  			var allSelectIds = [];
  			var paper = {};
  			var selectClazzIds = [];
  			
  			//显示已选择的知识点
  			function showTreeSelectedOfQuest(){
  				var ids = "#had_selected-"+curClickBtnIndex;
  				$(ids).html('已选择: ' + selectTexts.join('， '));
  				allSelectIds[curClickBtnIndex] = selectIds;
  				hideKnowledgeTree();
  			}
  			function hideKnowledgeTree(){
  				curClickBtnIndex = -1;
  				selectTexts = [];
  				$("#dialog_mask").hide();
  				$("#dialog").dialog("close");
  				$("#knowledge_tree").jstree().deselect_all(true);
  			}
  			//将数据拼接放入表单中，传到后台paper
  			function setInputArg(){
  				var questions = [];
  				paper.questions = questions;
  				paper.name = $("input[name='file']").val();
  				var str = selectClazzIds.join(",");
 	  			$("#clazzIds").val(str);
  				var kEmptyFlag = false;
  				$("#list li").each(function(i){
  					var quest = {};
  					try {
  	  					var kids = allSelectIds[(i+1)];
  	  					var len = kids.length;
  	  					var ks = [];
  	  					for ( var i = 0 ; i < len ; i++ ){
  	  						var k = {"id":kids[i]};
  	  						ks.push(k);
  	  					}
  	  					quest.knowledges = ks;
  	  					quest.flag = 1;
  	  					questions.push(quest);
					} catch (e) {
						kEmptyFlag = true;
						console.log(e);
					}
  				});
  				if ( !kEmptyFlag && str && paper && (paper.name !='') && paper.questions.length ){
  					$("#paperAttr").val(JSON.stringify(paper));
  					return true;
  				} else {
  					alert("文件，班级，题目和知识点都不能为空");
  					return false;
  				}
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
  			//刷新题号
  			function refreshQuestNum(){
  				$("span[id|=questnum]").each(function(i,ele){
  					$(this).html("题号："+(i+1)+"、");
  				});
  			}
  			function refreshClazzChecked(){
  				selectClazzIds = [];
  				$(".clazz_list input[type='checkbox']").each(function(i){
  	  					if ( this.checked ){
  	  						var len = "clazz".length;
  	  						var cid = this.id.substring(len);
  	  						selectClazzIds.push(cid);
  	  					} 
  	  			});
  				
  			}
			$("#add_quest").on({'click':function(){
  				var ulnode = $("#list");
  				var childnum = ulnode.children().length;
  				var questnum = (childnum+1);
  				var itemID = ++curMaxIndex;
  				var childnode = "<li><span id='questnum-"+questnum+"'>题号："+questnum+"、</span><input class='choose_knowledge_btn' type='button' value='选择知识点' id='"+itemID+"'/><span id='had_selected-"+itemID+"'></span><span class='delete_btn' id='delete-"+itemID+"'>删除</span></li>";
  				ulnode.append(childnode);
  				$( "#list li input[class='choose_knowledge_btn']" )
  						.unbind('click').bind({'click':function(){
								showKnowledgeTree($(this).attr('id'));
						}});
  				$("span[id|='delete']").last().unbind('click').bind({'click':function(){
  					$(this).parent().remove();
  					refreshQuestNum();
  				}});
  			}});
  			$(".clazz_list input[type='checkbox']").each(function(i){
  				$(this).on({"click":function(event){
  					refreshClazzChecked();
  				}});
  			});
  			$("#upload").button();
  			$("#add_quest").button();
  			$("#dialog").dialog({
  				minWidth : 666,
  				maxHeigth:560,
  				buttons: {
  			        "确定": showTreeSelectedOfQuest,
  			        "取消": function() {
  			        	hideKnowledgeTree();
  			        }
  			     },
  				close:function(){
  					hideKnowledgeTree();
  				}
  			});
  			$("#form").submit(function(event){
  				if (setInputArg()){
  					return;
  				}
				event.preventDefault();
  				
  			});
  			hideKnowledgeTree();
  			showResultIfNeed("${result}");
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
						<input type="hidden" name="paperStr" id="paperAttr"/>						
				  	</div>
				</div>
				<div class="submit_layer">
					<p id="input_tip">&nbsp;</p>
					<input type="submit" id="upload" value="开始上传"/>
				</div>
				<div class="clear"></div>
				<div class="separate_line"></div>
				<div class="paper_content_input">
					<div class="tip_text">保存到班级：</div>
					<div class="clazz_list">
						<% 
							if ( ListUtil.isNotEmpty(clazzs) ){
								for ( GroupInfo clazz : clazzs ){
									%>
									<input type="checkbox" id="clazz<%=clazz.getId()%>" />
									<label for="clazz<%=clazz.getId()%>"><%=clazz.getName()%></label>
									<%
								}
							}
						%>
					</div>
					<input type="hidden" name="clazzIds" id="clazzIds"/>
					<div class="tip_text">为试卷题目标注知识点：</div>
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
