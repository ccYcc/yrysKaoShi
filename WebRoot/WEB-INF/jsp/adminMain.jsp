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
  	<div class="center" id="admin_center">
  		<div class="content">
  			<p id="input_tip">&nbsp;</p>
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
