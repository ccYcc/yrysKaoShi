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
	List<ValidtionInfo> results = (List<ValidtionInfo>)request.getAttribute("messages");
	 
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
  	<script type="text/javascript" src="./js/render.js"></script>
  		
  	<script type="text/javascript">
  		$(function(){
  			var type = "<%=usertype%>";
	    	renderTabs(type,'消息中心',$(".cssmenu>ul"));
  			$("#dialog_mask").hide();
  			$(".actionBtns input[type='button']").button();
  			$("#actionsForm input").each(function(i){
  				$(this).unbind("click").bind({"click":function(){
  					$("input[name='action']").val($(this).attr("name"));
  					$("#actionsForm").submit();
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
				<% 
					if ( ListUtil.isNotEmpty(results) ){
						for ( ValidtionInfo info : results ){
							%>
							<div class="list_item">
								<div class="user_pic">
									<img class="user_pic_img" src="<%=UserServiceImpl.defaultHeadUrl%>"/>
								</div>
								<div class="actionBtns" >
									<form action="validations/handleActions" id="actionsForm" method="post">
										<input type="hidden" value="<%=info.getId()%>" name="id"/>
										<input type="hidden" value="<%=info.getGroupId()%>" name="groupId"/>
										<input type="hidden" value="<%=info.getAccept_id()%>" name="accept_id"/>
										<input type="hidden" value="<%=info.getRequest_id()%>" name="request_id"/>
										<input type="hidden" name="action"/>
										<%
											if ("老师".equals(usertype)){
												%>
													<input type="button" name="agree" value="同意"/>
													<input type="button" name="reject" value="拒绝"/>
												<%
											} else if ("学生".equals(usertype)){
												%>
													<input type="button" name="delete" value="删除"/>
												<%
											}
										%>
									</form>
								</div>
								<div class="valid_detail">
									<p class="valid_requester"><%=info.getRequest_id()%></p>
									<p class="valid_msg"><%=info.getMessage()%></p>
								</div>
							</div>
							<%
						}
					} else {
						%>
						<p>暂无消息</p>
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
