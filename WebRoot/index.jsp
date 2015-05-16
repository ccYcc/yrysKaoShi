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
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/jquery-ui.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/jquery-ui.structure.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/jquery-ui.theme.min.css">
  	<script src="<%=request.getContextPath() %>/js/jquery-1.11.3.min.js"></script>
  	<script src="<%=request.getContextPath() %>/js/jquery-ui.min.js"></script>
	<style type="text/css">
		a{
			display: block;
		}
	</style>
	    <script>

 		$(function() {
 	    	$( "#tomain" ).button();
 			$( "#tabs" ).tabs();
 			var tips = $("#dialog_content").text();
 			if (tips != ''){
 				$("#dialog").dialog();
 			}
 	  	});
 		
	</script>
  </head>
	  
<body>
  
	<label for="autocomplete">Select a programming language: </label>
	<input id="autocomplete">
	<button>Button label</button>
    <a href="jsp/toUploadFile" id="touploadfile">测试上传文件</a>
    <a href="jsp/login" id="tomain">看登录页面</a>
    <a href="jsp/toAdminMain" id="toAdminMain">看管理员主页</a>
</body>
</html>
