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
	<link rel="stylesheet" type="text/css" href="./css/student-main.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css">
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
  		});
  	</script>
  </head>
  
  <body>
  	<div id="stu_header">
  		<div class="stu_header_box">
  		  	<div class="logo">
  				<img alt="logo" src="img/logo.png"/>
	  		</div>
	  		<div class="search_bar">
	  			<input type="text" class="search_input"/>
	  			<input type="button" value="搜索"/>
	  		</div>
	  		<div class="nav_bar">
	  			<div class="nav_bar_box">
	  				<a href="#">消息</a>
	  				<a href="#">设置</a>
	  				<a href="#">
	  					<img class="user_logo" src="img/logo.png"/>
	  					<span class="user_name">${sessionScope.session_user.username}</span>
	  				</a>
	  				
	  			</div>
	  		</div>
  		</div>
  	</div>
  	<div class="center" id="stu_center">
  		<div id="left_nav">
  			<ul>
  				<li><a href="#">练习本</a></li>
  				<li><a href="#">自主测试</a></li>
  				<li><a href="#">考试情况</a></li>
  			</ul>
  		</div>
  		<div class="content">
  		</div>
  	</div>
  	<div class="footer" id="footer">
  		<div>© 2015 朝阳创新工作室版权所有</div>
  	</div>
  </body>
</html>
