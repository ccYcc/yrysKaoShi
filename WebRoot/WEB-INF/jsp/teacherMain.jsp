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
		<title>个人主页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
		<link href="./css/template-style.css" rel="stylesheet" type="text/css" media="all" />
		<link href="./css/globe.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="./js/render.js"></script>
		<script type="text/javascript" src="./js/jquery-1.11.3.min.js"></script>
		
		<script type="text/javascript">
		    $(window).load(function() {
		    	renderTabs(type_teacher,'首页',$(".cssmenu>ul"));
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
	<div class='h_btm'>
		<div class='cssmenu'>
			<ul>
			    <li class='active'><a href=''><span>首页</span></a></li>
			    <li><a href=''><span>我的班级</span></a></li>
			    <li><a href='jsp/editUser'><span>个人中心</span></a></li>
			    <li class='has-sub'><a href=''><span>消息中心</span></a></li>
			    <li class='last'><a href=''><span>帮助</span></a></li>
			 </ul>
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
							<a href="exam/type/exercise" target="_blank">
								<img src="img/icon1.jpg" class="feature_img"/>
							</a>
						  <h3>上传试卷</h3>
						  <p>将试卷的pdf，图片格式文件上传并标注试卷中每一道题的知识点，让学生通过核对方式提交答题情况，得到系统推荐的资源。</p>
					</div>
					<div class="grid_1_of_3 images_1_of_3">
						  <a href="exam/type/recommend" target="_blank">
						  	<img src="img/icon2.jpg"/>
						  </a>
						  <h3>创建班级</h3>
						  <p>选定要考察的知识点，个性化推荐题目。</p>
					</div>
					<div class="grid_1_of_3 images_1_of_3">
						  <img src="img/icon3.jpg"/>
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
