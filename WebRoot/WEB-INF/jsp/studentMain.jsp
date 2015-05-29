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
		<script type="text/javascript" src="./js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="./js/jquery.nivo.slider.js"></script>
		<script type="text/javascript" src="./js/render.js"></script>
		
		<script type="text/javascript">
		    $(function(){
		    	renderTabs(type_student,'首页',$(".cssmenu>ul"));
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
				<img class="head_user_img" id="photo" alt="" src="${sessionScope.session_user.headUrl}" />
				${sessionScope.session_user.username}
			</a>
			<a href="user/loginOut.do">登出</a>
		</div>	
		<div class="clear"></div>
	</div>
	<div class='h_btm'>
		<div class='cssmenu'>
			<ul>
			    <li><a><span>首页</span></a></li>
			    <li><a><span>我的关注</span></a></li>
			    <li><a><span>个人中心</span></a></li>
			    <li><a><span>消息中心</span></a></li>
			    <li><a><span>帮助</span></a></li>
			 </ul>
		</div>
	<div class="search">
    	<form action="user/service/search" method="post">
    		<input type="text" value="" name="searchText" placeholder="查找老师" autocomplete="off"/>
    		<input type="submit" value=""/>
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
							<a href="exam/type/exercise" target="_blank">
								<img src="img/icon1.jpg" class="feature_img"/>
							</a>
						  <h3>练习本</h3>
						  <p>选定要考察的知识点，随机出题。</p>
					</div>
					<div class="grid_1_of_3 images_1_of_3">
						  <a href="exam/type/recommend" target="_blank">
						  	<img src="img/icon2.jpg"/>
						  </a>
						  <h3>自主测试</h3>
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
