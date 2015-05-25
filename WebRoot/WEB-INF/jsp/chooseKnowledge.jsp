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
    
    <title>选择题目属性</title>
    
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<link rel="stylesheet" href="./js/themes/default/style.css" />
	<link rel="stylesheet" type="text/css" href="./css/globe.css"/>
	<link href="./css/template-style.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="./css/choose-knowledge.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css"/>
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	<script src="./js/jstree.min.js"></script>
  	<script type="text/javascript" src="./js/render.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
	    	renderTabs(type_student,0,$(".cssmenu>ul"));
  			var selectIds = [];
  			var select_tip_text = '';
  			$( "#levels" ).buttonset();
  			$( "#answer_types" ).buttonset();
  			$( "#start_test_btn" ).button();
  			function showInputTip(tip){
	 			$( "#input_tip" )
	 			.css({"color":"#f00"})
	 			.text( tip ).show()
	 			.fadeOut(2500,function(){
	 				$(this).html("&nbsp").show();
	 			});
	 		}
  			$("#form").submit(function(event){
				
				if ( selectIds != ''){
					return;
				}
				select_tip_text = '请先选择知识点...';
				showInputTip(select_tip_text);
				event.preventDefault();
			});
  			function onTreeChanged(e, data){
	  		    var i, j, r = [];
	  		  	selectIds = [];
	  		    for(i = 0, j = data.selected.length; i < j; i++) {
	  		    	var node = data.selected[i];
	  		      r.push(data.instance.get_node(node).text);
	  		      selectIds.push(data.instance.get_node(node).id);
	  		    }
	  		    $('#show_selected').html('已选择的知识点: ' + r.join(', '));
	  		  	$('#selected_ids').val(selectIds.join(','));
  			}
  			$("#knowledge_tree")
  		  		// listen for event
	  		 .on('changed.jstree', function (e, data) {
	  			onTreeChanged(e,data);
	  		  })	  		  
  		  	.jstree({
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
					<img class="head_user_img" id="photo" alt="" src="${sessionScope.session_user.headUrl}"/>
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
			<form action="exam/startExam" id="form" method="post">
				<div class="choose_level">
					<div id="levels">
						<span>选择题目难度:</span>
					    <input type="radio" id="easy" name="level" value="简单"/>
					    <label for="easy">简单</label>
					    <input type="radio" id="normal" name="level" value="一般" checked="checked"/>
					    <label for="normal">一般</label>
					    <input type="radio" id="hard" name="level" value="困难"/>
					    <label for="hard">困难</label>
				  	</div>
				</div>
				<div class="choose_answer_type">
					<div id="answer_types">
						<span>选择答题方式:</span>
					    <input type="radio" id="fast_type" name="answerType" value="fast"  checked="checked"/>
					    <label for="fast_type">快速答题</label>
					    <input type="radio" id="normal_type" name="answerType" value="normal"/>
					    <label for="normal_type">正常答题</label>
				  	</div>
				</div>
				<div class="submit_layer">
					<p id="input_tip">&nbsp;</p>
					<input type="hidden" id="selected_ids" name="selectedIds"/>
					<input type="hidden" value="${examType}" name="examType"/>
					<input type="submit" id="start_test_btn" value="开始测试"/>
				</div>
				
			</form>
				<div class="choose_knowledge_content">
					<div class="choose_knowledge_text">
						选择测试的知识点：
					</div>
					<div id="knowledge_tree"></div>
				</div>
		</div>
  </body>
</html>
