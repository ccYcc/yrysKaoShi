<%@page import="com.ccc.test.utils.StringUtil"%>
<%@page import="com.ccc.test.utils.PropertiesUtil"%>
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
    
    <title>统计信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="./css/globe.css">
	<link rel="stylesheet" type="text/css" href="./css/statistic.css">
	<link href="./css/template-style.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css">
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	<script src="./js/highcharts.js"></script>
  	<script src="./js/datepicker-zh-cn.js"></script>
  	<script type="text/javascript" src="./js/render.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
  			renderTabs(type_amdin,'统计信息',$(".cssmenu>ul"));
  			renderUserHead(type_amdin);
  			
  			$( "#datepickerOfHuoyueOfDay" ).datepicker({
				changeMonth: true,
				changeYear: true,
				dateFormat: "yy-mm-dd",
				maxDate: "-0y",
				minDate: "-100y",
				regional: "zh-CN" ,
				defaultDate:'+0',
				onSelect:function(){
					var ms = $( "#datepickerOfHuoyueOfDay" ).datepicker( "getDate" ).valueOf();
					console.log($( "#datepickerOfHuoyueOfDay" ).datepicker( "getDate" ));
					showCharOfDay(ms);
				}
  		    });
  			function showChart(title,jsonurl,arg,chartid){
  				$.ajax({
					method:"post",
					dataType : "json",
					data:arg,
					url:jsonurl,
					beforeSend:function(){
					},
					success:function(data){
						console.log(data);
						var xAxisArr = [];var yAxisArr = [];
						for ( var key in data ){
							xAxisArr.push(key);
							yAxisArr.push(data[key]);
						}
						$(chartid).highcharts({
			  		        title: {
			  		            text: title,
			  		            x: -20 //center
			  		        },
			  		        xAxis: {
			  		            categories: xAxisArr
			  		        },
			  		        yAxis: {
			  		            title: {
			  		                text: '用户数(人)'
			  		            },
			  		            plotLines: [{
			  		                value: 0,
			  		                width: 1,
			  		                color: '#808080'
			  		            }]
			  		        },
			  		        tooltip: {
			  		            valueSuffix: '人'
			  		        },
			  		        legend: {
			  		            layout: 'vertical',
			  		            align: 'right',
			  		            verticalAlign: 'middle',
			  		            borderWidth: 0
			  		        },
			  		        series: [{
			  		            name: ' ',
			  		            data: yAxisArr
			  		        }]
			  		    });
						
					},
					error:function(){
						alert("获取统计信息失败！");
					}
			});
  			}
  			function showCharOfDay(dayms){
  				showChart("日活跃用户","json/getHuoyueOfDay",{'daytime':dayms},"#chart_huoyue_hour");
  			}
  			showCharOfDay(new Date().valueOf());
  			
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
				<a href="javascript:void(0)"  id="head_icon_link">
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
			    <li><a><span>个人中心</span></a></li>
			 </ul>
		</div>
	<div class="clear"></div>
	</div>
		</div>
		</div>
	</div>
 		<div class="content">
 			<div class="onlinediv">
 				<div class="tip_box text_tip">
 					<span>当前在线人数：</span><span class="onlinenum"><%=StringUtil.getDefaultStrIfNull(application.getAttribute("onLineUserNum"), "未知") %></span>
 				</div>
 				
 			</div>
 			<div class="regsiterdiv">
 				<div class="tip_box text_tip">
 					<div class="chart"  style="min-width:400px;height:400px">月注册用户数</div>
 				</div>
 				
 			</div>
 			<div class="huoyue_day">
 				<div class="tip_box text_tip">
 					<span><input type="text" id="datepickerOfHuoyueOfMonth"/>月活跃用户数</span><br/>
 					<div class="chart">sdf</div>
 				</div>	
 			</div>
 			<div class="huoyue_hour">
 				<div class="tip_box text_tip">
 					<span><input type="text" id="datepickerOfHuoyueOfDay"/>日活跃用户数</span><br/>
 					<div class="chart" id="chart_huoyue_hour"></div>
 				</div>
 			</div>
 		</div>
  	<div class="footer" id="footer">
  		<div><%=PropertiesUtil.getString("CopyrightStr") %></div>
  	</div>
  </body>
</html>
