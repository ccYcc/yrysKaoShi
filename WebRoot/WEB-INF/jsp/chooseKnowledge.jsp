<%@page import="com.ccc.test.pojo.UserInfo"%>
<%@page import="com.ccc.test.utils.GlobalValues"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>选择题目属性</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="./js/themes/default/style.min.css" />
	<link rel="stylesheet" type="text/css" href="./css/globe.css">
	<link rel="stylesheet" type="text/css" href="./css/choose-knowledge.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css">
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	<script src="./js/jstree.min.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
  			$( "#levels" ).buttonset();
  			$( "#start_test_btn" ).button();
  			$("#knowledge_tree")
  		  		// listen for event
	  		 .on('changed.jstree', function (e, data) {
	  		    var i, j, r = [];
	  		    var selectIds = [];
	  		    for(i = 0, j = data.selected.length; i < j; i++) {
	  		    	var node = data.selected[i];
	  		      r.push(data.instance.get_node(node).text);
	  		      selectIds.push(data.instance.get_node(node).id);
	  		    }
	  		    $('#show_selected').html('已选择的知识点: ' + r.join(', '));
	  		  	$('#selected_ids').val(selectIds.join(','));
	  		  })
  		  	.jstree({
  			  "core" : {
  			    "multiple" : true,
  			    "animation" : 0,
  			  	"themes" : {"icons" : false},
  				"data" :{
  					'url' :'json/getKnowledges/?lazy',
  				  	"dataType" : "json",
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
		<div class="content">
			<form action="test/startExam" target="_blank">
				<div class="choose_level">
					<div id="levels">
						<span>选择答题难度:</span>
					    <input type="radio" id="easy" name="level" value="easy">
					    <label for="easy">简单</label>
					    <input type="radio" id="normal" name="level" value="normal" checked="checked">
					    <label for="normal">一般</label>
					    <input type="radio" id="hard" name="level" value="hard">
					    <label for="hard">困难</label>
				  	</div>
				</div>
				<div class="choose_knowledge_content">
					<div class="choose_knowledge">
						选择要测试的知识点：
					</div>
					<div id="knowledge_tree"></div>
					<div id="show_selected"></div>
					<input type="hidden" id="selected_ids" name="selectedIds"/>
					<input type="text" id="selected_level" name="selectedLevel"/>
				</div>
				<div class="submit_layer">
					<input type="submit" id="start_test_btn" value="开始考试"/>
				</div>
			</form>
		</div>
  </body>
</html>
