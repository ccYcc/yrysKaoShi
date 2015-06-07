<%@page import="com.ccc.test.utils.NumberUtil"%>
<%@page import="com.ccc.test.pojo.DiyPaperInfo"%>
<%@page import="com.ccc.test.service.impl.UserServiceImpl"%>
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
	String usertype = user.getType();
	UserInfo student = (UserInfo)request.getAttribute("student");
	List<DiyPaperInfo> historys = (List<DiyPaperInfo>)request.getAttribute("historys");
	
	 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>"/> 
    
    <title>测试历史记录</title>
    
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<link rel="stylesheet" href="./js/themes/default/style.css" />
	<link rel="stylesheet" type="text/css" href="./css/globe.css"/>
	<link href="./css/template-style.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="./css/exam-history.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css"/>
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	<script type="text/javascript" src="./js/render.js"></script>
  		
  	<script type="text/javascript">
  		$(function(){
  			var type = "<%=usertype%>";
	    	renderTabs(type,'消息中心',$(".cssmenu>ul"));
	    	renderUserHead(type);
  			$("#dialog_mask").hide();
  			$("input[id|='history_detail']").button();
  			$("input[id|='history_detail']").each(function(i){
  				$(this).unbind("click").bind({"click":function(){
  					var tmp = "history_detail-".length;
  					var hid = $(this).attr("id").substring(tmp);
  					var uid = "<%=student.getId()%>";
  					location.href = "exam/historyDetail?hid="+hid+"&uid="+uid;
  				}});
  			});
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
				    <li ><a><span>首页</span></a></li>
				    <li><a><span>我的关注</span></a></li>
				    <li><a><span>个人中心</span></a></li>
				    <li><a><span>消息中心</span></a></li>
				    <li><a><span>帮助</span></a></li>
				 </ul>
			</div>
		<div class="clear"></div>
		</div>
		</div>
		</div>
		</div>
		<div class="content">
		<div class="result_list">
			<div class="table_header"><%=student.getUsername()%>的历史测试：</div>
			<div class="list_table">
			<table>
  					<% 
  						if ( ListUtil.isNotEmpty(historys) ){
  							%>
  	  						<tr class="first_row">
	  	  						<td>序号</td>
	  	  						<td>试卷名称</td>
	  	  						<td>测试时间</td>
	  	  						<td>使用时间</td>
	  	  						<td>正确率</td>
	  	  						<td></td>
  	  						</tr>
  	  						<%
  							int i = 0;
  							for ( DiyPaperInfo info : historys ){
  								i++;
  								String createTime = TimeUtil.format(info.getCreateTime(), "yyyy年MM月dd日");
  								float scoreRate = 0;
  								if ( info.getRightCounts() <= 0 && info.getWrongCounts() <= 0 ){
  								} else {
									scoreRate = 100f * info.getRightCounts() / (info.getRightCounts() + info.getWrongCounts());  									
  								}
  								String shortName = info.getPaperName();
  								if ( shortName.length() > 20 ){
  									shortName = shortName.substring(0,20)+"...";
  								}
  								String rateStr = NumberUtil.formatNumber(scoreRate, "##.#");
  								%>
	  								<tr>
				  						<td><%=i%></td>
				  						<td title="<%=info.getPaperName() %>"><%=shortName %></td>
				  						<td><%=createTime%></td>
				  						<td><%=info.getUseTime()%>秒</td>
				  						<td><%=rateStr%>%</td>
				  						<td><input type="button" value="查看" id="history_detail-<%=info.getPid()%>"/></td>
				  					</tr>
  								<%
  							}
  						} else{
  							%>
  								<p>暂无测试记录</p>
  							<%
  						}
					%>
  				</table>
			</div>
			</div>
		</div>
	 	<div id="dialog_mask" >
			<div id="dialog" title="">
			</div>
		</div>
  </body>
</html>
