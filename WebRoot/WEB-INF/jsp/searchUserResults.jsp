<%@page import="com.ccc.test.utils.ListUtil"%>
<%@page import="com.ccc.test.pojo.UserInfo"%>
<%@page import="com.ccc.test.utils.GlobalValues"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<% 
	List<UserInfo> results = (List<UserInfo>)request.getAttribute("results");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>"/>
    
    <title>搜索结果</title>
    
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<link rel="stylesheet" href="./js/themes/default/style.css" />
	<link rel="stylesheet" type="text/css" href="./css/globe.css"/>
	<link href="./css/template-style.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="./css/search-result.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css"/>
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	<script src="./js/jstree.min.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
  			$("#searchBtn").button();
  			$("#dialog_mask").hide();
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
		<div class="header_bottom_line"></div>
		<div class="content">
			<form action="user/search" id="form" method="post">
				<div class="submit_layer">
					<input type="text" id="searchText" name="searchText"/>
					<input type="submit" id="searchBtn" value="搜索"/>
				</div>
				<div class="separate_line"></div>
			</form>
			<div class="result_list">
				<% 
					if ( ListUtil.isNotEmpty(results) ){
						for ( UserInfo user : results ){
							%>
							<div class="list_item">
								<div class="user_pic">
									<img class="user_pic_img" src="<%=user.getHeadUrl()%>"/>
								</div>
								<div class="actionBtns">
									<input value="加入他/她的班级" type="button"/>
								</div>
								<div class="user_detail">
									<p class="username"><%=user.getUsername()%></p>
									<p class="user_desc"><%=user.getDescription()%></p>
									<p class="username"><%=user.getUsername()%></p>
									<p class="user_desc"><%=user.getDescription()%></p>
									<p class="username"><%=user.getUsername()%></p>
									<p class="user_desc"><%=user.getDescription()%></p>
									<p class="username"><%=user.getUsername()%></p>
									<p class="user_desc"><%=user.getDescription()%></p>
								</div>
							</div>
							<%
						}
					} else {
						%>
						<p>没有查找到相关信息</p>
						<%
					}
				%>
			</div>
		</div>
		<div id="dialog_mask" >
			<div id="upload_dialog" title="">
			</div>
		</div>
  </body>
</html>
