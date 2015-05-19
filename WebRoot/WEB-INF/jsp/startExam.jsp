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
    
    <title>测试中</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="./css/globe.css">
	<link rel="stylesheet" type="text/css" href="./css/start-test.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css">
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
  			$( ".answer" ).buttonset();
  			$( "#submit" ).button();
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
  	<div id="test_center">
  		<div class="content">
  			<form action="" method="post">
  				<div>
  					题目难度：<span id="level">难${level}</span>
  					使用时间：<span id="used_time"></span>
  				</div>
  			  	<img src="img/1.jpg" id="question_img"/>
	  			<div class="answer">
	  				<input type="radio" value="A" name="answer" id="r1"/>
	  				<label for="r1">A</label>
	  				<input type="radio" value="B" name="answer" id="r2"/>
	  				<label for="r2">B</label>
	  				<input type="radio" value="C" name="answer" id="r3"/>
	  				<label for="r3">C</label>
	  				<input type="radio" value="D" name="answer" id="r4"/>
	  				<label for="r4">D</label>
	  				<input type="radio" value="会" name="answer" id="r5"/>
	  				<label for="r5">会</label>
	  				<input type="radio" value="不会" name="answer" id="r6" checked="checked"/>
	  				<label for="r6">不会</label>
	  			</div>
	  			<input type="submit" value="提交" id="submit"/>
  			</form>
  		</div>
  	</div>
  	<div class="footer" id="footer">
  		<div>© 2015 朝阳创新工作室版权所有</div>
  	</div>
  </body>
</html>
