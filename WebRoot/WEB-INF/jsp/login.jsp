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
		<title>登录</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
		<link href="./css/template-style.css" rel="stylesheet" type="text/css" media="all" />
		<link href="./css/globe.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css"/>
		<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css"/>
		<link href="./css/login.css" rel="stylesheet" type="text/css" media="all"/>
		<script type="text/javascript" src="./js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="./js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="./js/render.js"></script>
		
		<script type="text/javascript">
		    $(window).load(function() {
		    	
		        function showInputTip(tip){
		 			$( "#input_tip" )
		 			.text( tip ).show();
		 		}
		        var result = "${result}";
		        showInputTip(result);
		 		$("input[title]").tooltip();
				$( "#accordion" ).accordion();
				$("input[type=submit]").button();
				$( "fieldset select" ).selectmenu();
				
				var input_tip_text = '';
				$("#login_form").submit(function(event){
					var name = $("#login_username").val();
					var psw = $("#login_password").val();
					if ( name != '' && psw != '' ){
						return;
					}
					if ( name == '' ){
						input_tip_text = '请输入用户名！';
					} else if ( psw == '' ){
						input_tip_text = '请输入密码！';
					}
					showInputTip(input_tip_text);
					event.preventDefault();
				});
				$("#reg_form").submit(function(event){
					var name = $("#reg_username").val();
					var psw = $("#reg_password").val();
					var cpsw = $("#con_password").val();
					if ( name != '' && psw != '' && cpsw != '' && psw == cpsw){
						return;
					}
					if ( name == '' ){
						input_tip_text = '请输入用户名！';
					} else if ( psw == '' ){
						input_tip_text = '请输入密码！';
					} else if ( psw != cpsw ){
						input_tip_text = '两次密码不一致!';
					}
					showInputTip(input_tip_text);
					event.preventDefault();
				});
				
				$("input").autocomplete();
		    });
		</script>
	</head>
  
<body>
	<div class="login_main">
		<div id="login_header">
			<img src="./img/logo.png" class="logo_img"/>
			<label class="login_app_name">一人一师个性化考试系统</label>
		</div>
		<div class="content_bg">
			<div class="reg_login_box">
			<p id="input_tip">&nbsp;</p>
			<div id="accordion">
			  <h3 class="accordion_section">用户登录</h3>
			  <div id="login_tab">
			    <form action="user/login.do" method="post" id="login_form">
			    	<label for="#login_username">用户名：</label>
			  		<input id="login_username" name="username"  />
			  		<br/>
			  		<label for="#login_password">密　码：</label>
					<input id="login_password" name="password" type="password" />
					<br/>
					<fieldset>
					    <label for="usertype">身　份：</label>
					    <select name="usertype" id="login_usertype">
					      <option>学生</option>
					      <option>老师</option>
					      <option>管理员</option>
					    </select>
					</fieldset>
					<input type="submit" value="登录" id="submit_login"/>
			  	</form>
			  </div>
			  <h3 class="accordion_section">注册用户</h3>
			  <div id="reg_tab">
  			    <form action="user/register.do" method="post" id="reg_form">
  			    	<label for="#reg_username">用户名：</label>
			  		<input id="reg_username" name="username"  />
			  		<br/>
			  		<label for="#reg_password">密　码：</label>
					<input id="reg_password" name="password" type="password" title="密码长度6-20个字符"/>
					<br/>
					<label for="#con_password">确认密码:</label>
					<input id="con_password" name="con_password" type="password"/>
					<br/>
					<fieldset>
					    <label for="usertype">身　份：</label>
					    <select name="usertype" id="reg_usertype">
					      <option>学生</option>
					      <option>老师</option>
					    </select>
					</fieldset>
					<input type="submit" value="注册" id="submit_reg"/>
			  	</form>
			  </div>
			</div>
		</div>
		</div>
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
