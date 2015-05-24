<%@page import="com.ccc.test.pojo.ValidtionInfo"%>
<%@page import="com.ccc.test.utils.Bog"%>
<%@page import="com.ccc.test.utils.StringUtil"%>
<%@page import="com.ccc.test.utils.TimeUtil"%>
<%@page import="java.util.concurrent.TimeUnit"%>
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
	UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalValues.SESSION_USER);
	if ( user == null ){
		user = new UserInfo();
	}
	 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>"/>
    
    <title>消息中心</title>
    
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<link rel="stylesheet" href="./js/themes/default/style.css" />
	<link rel="stylesheet" type="text/css" href="./css/globe.css"/>
	<link href="./css/template-style.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="./css/message.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css"/>
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	<script src="./js/jstree.min.js"></script>
  	<script src="./js/datepicker-zh-cn.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
  		});
  	</script>
  </head>
  
  <body>
	  <div class="btm_border">
		<div class="h_bg">
		<div class="wrap">
		<div class="header">
			<div class="logo">
				<h1><a href="javascript:void(0)"><img src="img/logo1.png" alt=""/></a></h1>
			</div>
			<div class="user-icon">
				<a href="javascript:void(0)">
					<img id="photo" alt="" src="${sessionScope.session_user.headUrl}" width="48px" height="48px"/>
					${sessionScope.session_user.username}
				</a>
				<a href="user/loginOut.do">登出</a>
			</div>	
			<div class="clear"></div>
		</div>
		<div class='h_btm'>
			<div class='cssmenu'>
				<ul>
				    <li ><a href='jsp/toStudentMain2'><span>首页</span></a></li>
				    <li><a href=''><span>我的关注</span></a></li>
				    <li><a href='jsp/editUser'><span>个人中心</span></a></li>
				    <li class='active'><a><span>消息中心</span></a></li>
				    <li class='last'><a href='contact.html'><span>帮助</span></a></li>
				 </ul>
			</div>
		<div class="clear"></div>
		</div>
		</div>
		</div>
		</div>
		<div class="content">
		<div class="result_list">
				<% 
					List<ValidtionInfo> results = null;
					if ( ListUtil.isNotEmpty(results) ){
						for ( ValidtionInfo info : results ){
							%>
							<div class="list_item">
								<div class="user_pic">
									<img src="<%=user.getHeadUrl()%>"/>
								</div>
								<div class="followbtn">
								</div>
								<div class="user_detail">
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
			<div id="upload_dialog" title="上传头像文件">
				<form enctype ="multipart/form-data" action="user/service/uploadPhoto" method="post">
					<input name="file" type="file" accept=".png,.jpg"/>
					<input type="submit" value="保存"/>
				</form>
			</div>
		</div>
  </body>
</html>
