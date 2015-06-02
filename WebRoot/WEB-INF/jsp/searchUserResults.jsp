<%@page import="com.ccc.test.pojo.GroupInfo"%>
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
	List<UserInfo> results = (List<UserInfo>)request.getAttribute("users");
	String sText = (String)request.getAttribute("searchText");
	sText = sText == null ? "":sText;
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
  	<script type="text/javascript" src="./js/render.js"></script>
  		
  	<script type="text/javascript">
  		$(function(){
  			var type = "<%=usertype%>";
  			renderUserHead(type);
  			$("#searchBtn").button();
  			var curTeacherId = 0;//当前请求的老师的id
  			function joinGroup(){
  				var tid = $("form input[name='tid']").val();
				var clazzs = $("form input[name='clazzs']").val();
				console.log(tid+" " + clazzs);
				if ( tid && clazzs ){
					$("#joinForm").submit();
					hideDialog();
					return;
				}
				if ( !tid ){
					alert("请求错误，请选择老师");
				} else {
					alert("您没有选择班级");
				}
  				
  			}
  			
  			function hideDialog(){
  				$("#dialog_mask").hide();
  				$("#dialog").dialog('close');
  				var clazzsChild = $("#clazz_list-"+curTeacherId);
  				clazzsChild.addClass("hide");
  				clazzsChild.appendTo("#item_id-"+curTeacherId);//返回dom节点到list item下 不显示
  				$("form input[name='tid']").val('');
  				$("form input[name='clazzs']").val('');
  			}
  			function showDialog(tid){
  				curTeacherId = tid;
  				$("#dialog_mask").show();
  				$("#dialog").dialog('open');
  				var clazzsChild = $("#clazz_list-"+tid);
  				clazzsChild.removeClass("hide");
  				clazzsChild.appendTo("#dialog");//移动dom节点到dialog下显示
  				$("form input[name='tid']").val(tid);
  			}
  			function refreshClazzChecked(){
  				selectClazzIds = [];
  				$(".clazz_list input[type='checkbox']").each(function(i){
  	  					if ( this.checked ){
  	  						var len = "clazz-".length;
  	  						var cid = this.id.substring(len);
  	  						selectClazzIds.push(cid);
  	  					} 
  	  			});
  				$("form input[name='clazzs']").val(selectClazzIds.join(','));
  			}
  			$("input[id|='see_tch_btn']").each(function(i){
  				$(this).button();
  				$(this).on({"click":function(event){
  					var len = "see_tch_btn-".length;
  					var tid = this.id.substring(len);
  					showDialog(tid);
  				}});
  			});
  			$(".clazz_list input[type='checkbox']").each(function(i){
  				$(this).on({"click":function(event){
  					refreshClazzChecked();
  				}});
  			});
  			$("#dialog").dialog({
  				minWidth :666,
  				maxHeigth:560,
  				buttons: {
  					"发送请求": joinGroup,
  			        "取消": hideDialog
  			     },
  				close:hideDialog
  			});
  			hideDialog();
  			showResultIfNeed("${result}");
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
				<a href="javascript:void(0)" id="head_icon_link">
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
		<div class="content">
			<form action="user/service/search" id="form" method="post" id="searchForm">
				<div class="submit_layer">
					<input type="text" id="searchText" name="searchText" value="<%=sText%>" placeholder="输入老师的用户名或者真实姓名"/>
					<input type="submit" id="searchBtn" value="搜索"/>
				</div>
				<div class="separate_line"></div>
			</form>
			<div class="result_list">
				<% 
					if ( ListUtil.isNotEmpty(results) ){
						for ( UserInfo uinfo : results ){
							%>
							<div class="list_item" id="item_id-<%=uinfo.getId()%>">
								<div class="user_pic">
									<img class="user_pic_img" src="<%=uinfo.getHeadUrl()%>"/>
								</div>
								<div class="actionBtns">
									<input value="加入Ta的班级" type="button" id="see_tch_btn-<%=uinfo.getId()%>"/>
								</div>
								<div class="user_detail">
									<p class="username"><%=uinfo.getUsername()%></p>
									<p class="user_desc"><%=uinfo.getDescription()%></p>
								</div>
								<div class="clazz_list hide" id="clazz_list-<%=uinfo.getId()%>">
									<% 
										List<GroupInfo> clazzs = uinfo.getClasses();
										if ( ListUtil.isNotEmpty(clazzs) ){
											for ( GroupInfo clazz : clazzs ){
												%>
												<input type="checkbox" id="clazz-<%=clazz.getId()%>" />
												<label for="clazz-<%=clazz.getId()%>"><%=clazz.getName()%></label>
												<%
											}
										} else {
											%>
												<p>暂无班级</p>
											<%
										}
									%>
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
			<div id="dialog" title="Ta的班级">
				<form action="user/service/joinGroup" method="post" id="joinForm">
					<input type="hidden" name="clazzs"/>
					<input type="hidden" name="tid"/>
					<input type="hidden" id="searchText" name="searchText" value="<%=sText%>"/>
				</form>
			</div>
		</div>
  </body>
</html>
