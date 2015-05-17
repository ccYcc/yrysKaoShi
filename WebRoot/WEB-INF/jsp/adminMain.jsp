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
    
    <title>个人中心</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="./css/globe.css">
	<link rel="stylesheet" type="text/css" href="./css/admin-main.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css">
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
  			$( "#accordion" ).accordion();
  			$("input[type=submit]").button();
  			$( "fieldset select" ).selectmenu();
  		});
  	</script>
  </head>
  
  <body>
  	<div id="header">
  		<div class="header_box">
  		  	<div class="logo">
  				<img alt="logo" src="img/logo.png"/>
	  		</div>
	  		<div class="nav_bar">
	  			<div class="nav_bar_box">
	  				<a href="jsp/main.do" target="_blank">设置</a>
	  				<a href="jsp/main.do" target="_blank">
	  					<img class="user_logo" src="img/logo.png"/>
	  					<span class="user_name">${sessionScope.session_user.username}</span>
	  				</a>
	  				
	  			</div>
	  		</div>
  		</div>
  	</div>
  	<div class="center" id="admin_center">
  		<div class="content">
  			<p id="input_tip">&nbsp</p>
			<div id="accordion">
			  <h3 class="accordion_section">上传知识点</h3>
			  <div id="knowledge_tab">
			        <form name="knowledgeForm" action="file/upload.do" method="post" enctype="multipart/form-data">  
					        选择文件：<input type="file" name="file" accept="image/*">  
					   <br/>  <input type="submit" value="提交">  
					 </form>
			  </div>
			  <h3 class="accordion_section">上传题目</h3>
			  <div id="question_tab">
  			        <form name="questionForm" action="question/uploadQuestion" method="post" enctype="multipart/form-data">  
					       选择文件：<input type="file" name="file" accept="application/x-zip-compressed,application/x-rar-compressed">
					   <br> <input type="hidden" value="questions" name="category" />      
					   <br/> <input type="submit" value="提交">
					     
				    </form>
			  </div>
			</div>
  		</div>
  	</div>
  	<div class="footer" id="footer">
  		<div>© 2015 朝阳创新工作室版权所有</div>
  	</div>
  </body>
</html>
