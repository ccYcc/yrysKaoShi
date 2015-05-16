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
		<title>个人主页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link href="./css/style.css" rel="stylesheet" type="text/css" media="all" />
		<link href="./css/globe.css" rel="stylesheet" type="text/css">
		<!--slider-->
		<link href="./css/slider.css" rel="stylesheet" type="text/css" media="all"/>
		<script type="text/javascript" src="./js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="./js/jquery.nivo.slider.js"></script>
		<script type="text/javascript">
		    $(window).load(function() {
		        $('#slider').nivoSlider();
		    });
		</script>
	</head>
  
<body>
<!------ 头部 ------------>
	<div class="btm_border">
	<div class="h_bg">
	<div class="wrap">
	<div class="header">
		<div class="logo">
			<h1><a href="index.html"><img src="img/logo1.png" alt=""></a></h1>
		</div>
		<div class="user-icon">
			<a href="#">
				<img id="photo" alt="" src="img/icon2.jpg" width="48px" height="48px"/>
				${sessionScope.session_user.username}
			</a>
		</div>	
		<div class="clear"></div>
	</div>
	<div class='h_btm'>
		<div class='cssmenu'>
			<ul>
			    <li class='active'><a href='index.html'><span>首页</span></a></li>
			    <li><a href='about.html'><span>我的关注</span></a></li>
			    <li><a href='staff.html'><span>个人中心</span></a></li>
			    <li class='has-sub'><a href='service.html'><span>消息中心</span></a></li>
			    <li class='last'><a href='contact.html'><span>帮助</span></a></li>
			 </ul>
		</div>
	<div class="search">
    	<form>
    		<input type="text" value="" >
    		<input type="submit" value="">
    	</form>
	</div>
	<div class="clear"></div>
	</div>
	</div>
	</div>
	</div>
	<!------ 三个功能点区域 ------------>
	<div class="slider_bg">
	<div class="wrap">
			<div class="grids_1_of_3">
					<div class="grid_1_of_3 images_1_of_3">
							<a href="jsp/toChooseKnowledge" target="_blank">
								<img src="img/icon1.jpg" class="feature_img">
							</a>
						  <h3>练习本</h3>
						  <p>选定要考察的知识点，随机出题。</p>
					</div>
					<div class="grid_1_of_3 images_1_of_3">
						  <img src="img/icon2.jpg">
						  <h3>自主测试</h3>
						  <p>选定要考察的知识点，个性化推荐题目。</p>
					</div>
					<div class="grid_1_of_3 images_1_of_3">
						  <img src="img/icon3.jpg">
						  <h3>历史分析</h3>
						  <p>回顾以往历史答题情况</p>
					</div>
					<div class="clear"></div>
				</div>
	</div>
	</div>
	<!--底部区域-->
	<div class="main_bg">
		<!--footer-->
		<div class="ftr-bg">
			<div class="wrap">
				<div class="footer">
					<div class="copy">
						<p class="w3-link">&copy; Copyright 2015.朝阳工作室 All rights reserved.</p>
					</div>
				<div class="clear"></div>	
			</div>
			</div>
		</div>
	</div>
</body>
</html>
