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
	String birthdayText = TimeUtil.format(user.getBirthday(), "yyyy-MM-dd");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>"/>
    
    <title>个人中心</title>
    
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
  	<script src="./js/datepicker-zh-cn.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
  			var result = "${result}";
  			if ( result ){
  				alert(result);
  			}
  			var headurl = "<%=user.getHeadUrl()%>";
  			var sex = "<%=user.getSex()%>";
  			if ( headurl == 'null' ){
  				headurl = "./img/default_user_pic.jpg";
  			}
  			if ( sex == '男' ){
  				$("input[value='男']").attr({"checked":"checked"});
  			} else {
  				$("input[value='女']").attr({"checked":"checked"});
  			}
  			var bt = "<%=birthdayText%>";
  			$("#datepicker").val(bt);
  			$("#user_pic_img").attr({"src":headurl});
  			$("#submit").button();
  			$( "#datepicker" ).datepicker({
				changeMonth: true,
				changeYear: true,
				dateFormat: "yy-mm-dd",
				maxDate: "-6y",
				minDate: "-70y",
				regional: "zh-CN" ,
				onSelect:function(){
					var currentDate = $( "#datepicker" ).datepicker( "getDate" );
					var ms = currentDate.valueOf();
					$("#birthday").val(ms);
				}
  		    });
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
				<form action="user/service/update" method="post">
				<p>填写真实的资料，有助于熟人找到你哦</p>
				<p class="user_pic_layer">
					<label>当前头像：</label>
					<span class="user_pic">
						<a>
							<img src="" id="user_pic_img"/>
						</a>
						<input type="hidden" name="id" id="id" value="<%=user.getId()%>"/>
						<input type="hidden" name="headUrl" id="headUrl"/>
					</span>
				</p>

				<p>
					<label>真实名字：</label>
					<input type="text" name="realname" value="<%=user.getUsername()%>"/>
				</p>
				<p>
					<label>邮箱地址：</label>
					<input type="text" name="email"  value="<%=user.getEmail()%>"/>
				</p>
				<p>	<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生日：</label>
					<input type="text" id="datepicker" />
					<input type="hidden" id="birthday" name="birthday"  value="<%=user.getBirthday()%>" />
				</p>
				<p>
					<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性别：</label>
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
					<textarea rows="3" cols="50" name="description" ><%=user.getDescription()%></textarea>
				</p>
				<input type="submit" value="保存" id="submit"/>
				</form>
			</div>
		</div>
  </body>
</html>
