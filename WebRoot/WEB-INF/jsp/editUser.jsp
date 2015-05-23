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
	UserInfo user = (UserInfo)request.getAttribute("user");
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
	<link rel="stylesheet" type="text/css" href="./css/edit-user.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css"/>
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	<script src="./js/jstree.min.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
  		});
  	</script>
  </head>
  
  <body>
 		<div class="header">
			<div class="logo">
				<h1><a href="index.html"><img src="img/logo1.png" alt="" /></a></h1>
			</div>
			<div class="user-icon">
				<a href="#">
					<img id="photo" alt="" src="img/icon2.jpg" width="48px" height="48px"/>
					${sessionScope.session_user.username}
				</a>
			</div>	
			<div class="clear"></div>
		</div>
		<div class="header_bottom_line"></div>
		<div class="content">
			<div class="user_detail">
				<p>填写真实的资料，有助于熟人找到你哦</p>
				<div class="user_pic_layer">
					<label>当前头像：</label>
					<span class="user_pic">
						<a>
							<img src="./img/default_user_pic.jpg"/>
						</a>
					</span>
					<form name="knowledgeForm" action="file/upload.do" method="post" enctype="multipart/form-data">  
				        <input type="file"   name="file" accept="image/*"/>  
				    	<input type="submit" value="提交"/>  
					</form>
				</div>

				<p>
					<label>真实名字：</label>
					<input type="text" name="realname"/>
				</p>
				<p>
					<label>邮箱地址：</label>
					<input type="text" name="email"/>
				</p>
				<p>
					<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性&nbsp;别：</label>
					<label>
						<input type="radio" name="sex" value="男"/>
						男
					</label>
					<label>
						<input type="radio" name="sex" value="女" />
						女
					</label>
				</p>
				<p>
					<label>个人描述：</label>
					<textarea rows="3" cols="50" name="description" >
					</textarea>
				</p>
			</div>
		</div>
  </body>
</html>
