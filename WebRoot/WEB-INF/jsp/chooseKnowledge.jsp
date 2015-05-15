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
    
    <title>选择题目属性</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="./css/globe.css">
	<link rel="stylesheet" type="text/css" href="./css/choose-knowledge.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css">
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
  			$( "#levels" ).buttonset();
  			$( "#start_test_btn" ).button();
  		});
  	</script>
  </head>
  
  <body>
		<div class="content">
			<form action="jsp/toStartTest" target="_blank">
				<div class="choose_level">
					<div id="levels">
						<span>选择答题难度:</span>
					    <input type="radio" id="easy" name="radio">
					    <label for="easy">简单</label>
					    <input type="radio" id="normal" name="radio" checked="checked">
					    <label for="normal">一般</label>
					    <input type="radio" id="hard" name="radio">
					    <label for="hard">困难</label>
				  	</div>
				</div>
				<div class="choose_knowledge_content">
					<div class="choose_knowledge">
						选择要测试的知识点：
					</div>
					<div>
						<ul>
							<li>
								<ul>
									<li><input type="checkbox" value="cc">fds</li>
									<li><input type="checkbox" value="cc">fds</li>
									<li><input type="checkbox" value="cc">fds</li>
									<li><input type="checkbox" value="cc">fds</li>
								</ul>
							</li>
							<li><input type="checkbox" value="cc">trt</li>
							<li><input type="checkbox" value="cc"></li>
							<li><input type="checkbox" value="cc"></li>
							<li><input type="checkbox" value="cc"></li>
							<li><input type="checkbox" value="cc"></li>
							<li>
								<ul>
									<li><input type="checkbox" value="cc">gh</li>
									<li><input type="checkbox" value="cc"></li>
									<li><input type="checkbox" value="cc"></li>
									<li><input type="checkbox" value="cc">mnmh</li>
								</ul>
							</li>
							<li><input type="checkbox" value="cc"></li>
							
						</ul>
					</div>
				</div>
				<div class="submit_layer">
					<input type="submit" id="start_test_btn" value="开始考试"/>
				</div>
			</form>
		</div>
  </body>
</html>
